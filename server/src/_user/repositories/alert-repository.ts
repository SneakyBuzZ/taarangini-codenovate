import { RegisterAlertDTO } from "@/_user/dto";
import { ObjectId } from "mongodb";
import Alert from "@/_user/models/alerts-model";

export class AlertRepository {
  async save(data: RegisterAlertDTO, userId?: string) {
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

    //This part just returns the top 5 locations near the given lat and long
    // return Alert.find({
    //   coordinates: {
    //     $nearSphere: {
    //       $geometry: point,
    //       $maxDistance: maxDistanceMeters,
    //     },
    //   },
    // })
    //   .limit(limit)
    //   .lean();

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
}
