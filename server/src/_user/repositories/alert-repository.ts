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
      // UI filters
      time?: string;
      safety?: number;
    }
  ) {
    if (!ObjectId.isValid(alertId)) return null;

    // Defaults for all metrics
    const defaults = {
      crime: 0,
      accident: 0,
      response: 0,
      sos: 0,
      lighting: 0,
      cctv: 0,
      pedestrian: 0,
    };

    // Extract UI filters
    const timeFilter = metrics.time;
    const lightingFilter =
      typeof metrics.lighting === "number" ? metrics.lighting : undefined;
    const safetyFilter =
      typeof metrics.safety === "number" ? metrics.safety : undefined;

    // Compose metrics for formula (exclude non-numeric)
    const { time: _omitTime, safety: _omitSafety, ...numericMetrics } = metrics;
    const m = { ...defaults, ...numericMetrics } as Record<string, number>;

    // Default weights
    let weightsDefault = {
      wc: 0.3, // crime
      wa: 0.15, // accident
      wl: 0.15, // lighting
      wv: 0.1, // cctv
      wp: 0.07, // pedestrian
      wr: 0.08, // response
      ws: 0.15, // sos
    };

    // Adjust weights based on filters
    if (timeFilter === "night") {
      weightsDefault.wl = 0.3; // lighting more important at night
      weightsDefault.wc = 0.4; // crime more important at night
      // Aggressive weight adjustment based on filters
      if (timeFilter === "night") {
        weightsDefault = {
          wc: 0.45,
          wa: 0.25,
          wl: 0.05,
          wv: 0.05,
          wp: 0.03,
          wr: 0.07,
          ws: 0.1,
        };
      } else if (timeFilter === "day") {
        weightsDefault = {
          wc: 0.15,
          wa: 0.1,
          wl: 0.15,
          wv: 0.2,
          wp: 0.2,
          wr: 0.1,
          ws: 0.1,
        };
      }

      if (lightingFilter !== undefined && lightingFilter < 0.5) {
        weightsDefault = {
          wc: 0.1,
          wa: 0.1,
          wl: 0.5,
          wv: 0.1,
          wp: 0.05,
          wr: 0.05,
          ws: 0.1,
        };
      }

      if (safetyFilter !== undefined && safetyFilter < 0.5) {
        weightsDefault = {
          wc: 0.1,
          wa: 0.05,
          wl: 0.05,
          wv: 0.05,
          wp: 0.05,
          wr: 0.25,
          ws: 0.45,
        };
      }

      // Normalize weights so their sum is 1
      const totalWeight = Object.values(weightsDefault).reduce(
        (sum, val) => sum + val,
        0
      );
      (
        Object.keys(weightsDefault) as Array<keyof typeof weightsDefault>
      ).forEach((k) => {
        weightsDefault[k] = +(weightsDefault[k] / totalWeight);
      });
    }

    // Allow override from payload
    const w: Record<string, number> = {
      wc: typeof metrics.wc === "number" ? metrics.wc : weightsDefault.wc,
      wa: typeof metrics.wa === "number" ? metrics.wa : weightsDefault.wa,
      wl: typeof metrics.wl === "number" ? metrics.wl : weightsDefault.wl,
      wv: typeof metrics.wv === "number" ? metrics.wv : weightsDefault.wv,
      wp: typeof metrics.wp === "number" ? metrics.wp : weightsDefault.wp,
      wr: typeof metrics.wr === "number" ? metrics.wr : weightsDefault.wr,
      ws: typeof metrics.ws === "number" ? metrics.ws : weightsDefault.ws,
    };

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
