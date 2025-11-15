import { RegisterKycDTOType } from "@/_user/dto";
import Kyc from "@/_user/models/kyc-model";
import { AppError } from "@/utils/error";
import { ObjectId } from "mongodb";

export class KycRepository {
  async save(data: RegisterKycDTOType, userId: string) {
    const kyc = await Kyc.create({
      ...data,
      userId: new ObjectId(userId),
    });
    return kyc._id.toString();
  }

  async getByUserId(userId: string) {
    return Kyc.findOne({ userId: new ObjectId(userId) });
  }

  async getIdByUserId(userId: string) {
    const kyc = await Kyc.findOne({ userId: new ObjectId(userId) }, { _id: 1 });
    if (!kyc) throw new AppError(404, "KYC not found");
    return kyc?._id;
  }
}
