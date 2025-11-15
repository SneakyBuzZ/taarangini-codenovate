import { RegisterUserDTOType } from "@/_user/dto";
import User from "@/_user/models/user-model";
import Kyc from "@/_user/models/kyc-model";
import cuid from "cuid";
import { AppError } from "@/utils/error";
import { Types } from "mongoose";

export class UserRepository {
  async save(data: RegisterUserDTOType): Promise<string> {
    const user = await User.create({
      ...data,
      touristId: cuid(),
    });
    return user._id.toString();
  }

  async findById(id: string) {
    const user = await User.findById(id).select(
      "-hashedToken -__v -createdAt -updatedAt -onboardingStatus"
    );
    const kyc = await Kyc.findOne({ userId: user?._id }).select(
      "-_id -userId -__v -createdAt -updatedAt -emergencyContact"
    );
    return { ...user?.toObject(), kyc };
  }

  async setTouristId(userId: string, touristId: string) {
    await User.findByIdAndUpdate(userId, { touristId });
  }

  async setKycId(userId: string, kycId: string) {
    await User.findByIdAndUpdate(userId, { kycId });
  }

  async setItineraryId(userId: string, itineraryId: string) {
    await User.findByIdAndUpdate(userId, { itineraryId });
  }

  async getDetailsForHash(userId: string) {
    const user = (await User.findById(userId)
      .select("_id kycId itineraryId")
      .populate({
        path: "kycId",
        select: "docType docNumber emergencyContact",
      })
      .populate({
        path: "itineraryId",
        select: "arrivalDate departureDate",
      })
      .lean()) as IUser | null;

    if (!user) throw new AppError(404, "User not found");

    const kycDetails = user.kycId
      ? {
          userId: user._id,
          docType: user.kycId.docType,
          docNumber: user.kycId.docNumber,
          emergencyContact: user.kycId.emergencyContact,
        }
      : null;

    const itineraryDetails = user.itineraryId
      ? {
          itineraryId: user.itineraryId._id,
          arrivalDate: user.itineraryId.arrivalDate,
          departureDate: user.itineraryId.departureDate,
        }
      : null;

    const emergencyDetails = user.kycId
      ? {
          name: user.kycId.emergencyContact.name,
          phone: user.kycId.emergencyContact.phone,
          relation: user.kycId.emergencyContact.relation,
        }
      : null;

    return { kycDetails, itineraryDetails, emergencyDetails };
  }

  async setQrcode(userId: string, qrcode: string) {
    await User.findByIdAndUpdate(userId, { qrcode });
  }

  async getAll() {
    return await User.find().select("-hashedToken");
  }

  async update(id: string, update: any) {
    return await User.findByIdAndUpdate(id, update, { new: true });
  }

  async delete(id: string) {
    return await User.findByIdAndDelete(id);
  }

  async findByEmail(email: string) {
    return await User.findOne({ email });
  }

  async setToken(userId: string, token: string) {
    await User.findByIdAndUpdate(userId, { hashedToken: token });
  }

  async setOnboardingStatus(userId: string, status: number) {
    await User.findByIdAndUpdate(userId, { onboardingStatus: status });
  }
}

interface IKyc {
  _id: Types.ObjectId;
  docType: string;
  docNumber: string;
  emergencyContact: {
    name: string;
    phone: string;
    relation: string;
  };
}

interface IItinerary {
  _id: Types.ObjectId;
  arrivalDate: Date;
  departureDate: Date;
}

interface IUser {
  _id: Types.ObjectId;
  kycId?: IKyc;
  itineraryId?: IItinerary;
}
