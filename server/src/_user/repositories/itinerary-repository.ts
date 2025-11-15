import { RegisterItineraryDTOType } from "@/_user/dto";
import { ObjectId } from "mongodb";
import Itinerary from "@/_user/models/itinerary-model";

export class ItineraryRepository {
  async save(data: RegisterItineraryDTOType, userId: string, kycId: ObjectId) {
    const itinerary = await Itinerary.create({
      ...data,
      userId: new ObjectId(userId),
      kycId: kycId,
    });
    return itinerary._id.toString();
  }
}
