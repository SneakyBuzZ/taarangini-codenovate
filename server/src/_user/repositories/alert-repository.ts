import { RegisterAlertDTO, RegisterAlertDTOType } from "@/_user/dto";
import { ObjectId } from "mongodb";
import Alert from "@/_user/models/alerts-model";

export class AlertRepository {
  async save(data: RegisterAlertDTOType, userId?: string) {
    const payload: any = { ...data };

    //Not compulsory
    if (userId) {
      payload.reportedBy = new ObjectId(userId);
    }

    const alert = await Alert.create(payload);
    return alert._id.toString();
  }

  async getById(alertId: string) {
    return Alert.findById(alertId).lean();
  }

  async getNearestAlerts(
    latitude: number,
    longitude: number,
    maxDistanceMeters = 30000,
    limit = 5
  ) {
    const point = {
      type: "Point",
      coordinates: [longitude, latitude],
    };

    const pipeline = [
      {
        $geoNear: {
          near: point,
          distanceField: "distance", // returns meters
          maxDistance: maxDistanceMeters, // 30km default
          spherical: true,
          key: "coordinates",
        },
      },
      { $limit: limit },
      {
        $project: {
          image: 1,
          title: 1,
          description: 1,
          location: 1,
          severity: 1,
          categoryChip: 1,
          reportedBy: 1,
          coordinates: 1,
          distance: 1,
          createdAt: 1,
          updatedAt: 1,
        },
      },
    ];

    // cast pipeline to any to satisfy mongoose/mongodb typings
    return Alert.aggregate(pipeline as any).exec();
  }

  // new: compute safety using provided normalized metrics and persist into alert.safety
  async computeAndSaveSafety(
    alertId: string,
    metrics: {
      crime?: number;
      accident?: number;
      response?: number;
      sos?: number;
      lighting?: number;
      cctv?: number;
      pedestrian?: number;
      wc?: number;
      wa?: number;
      wl?: number;
      wv?: number;
      wp?: number;
      wr?: number;
      ws?: number;
    }
  ) {
    if (!ObjectId.isValid(alertId)) return null;

    const defaults = {
      crime: 0,
      accident: 0,
      response: 0,
      sos: 0,
      lighting: 0,
      cctv: 0,
      pedestrian: 0,
    };

    const m = { ...defaults, ...(metrics || {}) } as Record<string, number>;

    // default weights from your prompt
    const weightsDefault = {
      wc: 0.3,
      wa: 0.15,
      wl: 0.15,
      wv: 0.1,
      wp: 0.07,
      wr: 0.08,
      ws: 0.15,
    };

    const w = {
      ...weightsDefault,
      wc: metrics.wc ?? weightsDefault.wc,
      wa: metrics.wa ?? weightsDefault.wa,
      wl: metrics.wl ?? weightsDefault.wl,
      wv: metrics.wv ?? weightsDefault.wv,
      wp: metrics.wp ?? weightsDefault.wp,
      wr: metrics.wr ?? weightsDefault.wr,
      ws: metrics.ws ?? weightsDefault.ws,
    } as Record<string, number>;

    // formula: inner = wc*C + wa*A + wl*(1-L) + wv*(1-V) + wp*(1-P) + wr*R + ws*S
    const inner =
      w.wc * m.crime +
      w.wa * m.accident +
      w.wl * (1 - m.lighting) +
      w.wv * (1 - m.cctv) +
      w.wp * (1 - m.pedestrian) +
      w.wr * m.response +
      w.ws * m.sos;

    const clamp = Math.max(0, Math.min(1, inner));
    const score = +(100 * (1 - clamp)).toFixed(3);

    const update = {
      "safety.score": score,
      "safety.components": {
        crime: m.crime,
        accident: m.accident,
        response: m.response,
        sos: m.sos,
        lighting: m.lighting,
        cctv: m.cctv,
        pedestrian: m.pedestrian,
      },
      "safety.weights": w,
      "safety.computedAt": new Date(),
    };

    const updated = await Alert.findByIdAndUpdate(
      alertId,
      { $set: update },
      { new: true }
    ).lean();
    return updated?.safety ?? null;
  }
}
