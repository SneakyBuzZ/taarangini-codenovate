import { RegisterItineraryDTOType } from "@/_user/dto";
import { ItineraryRepository } from "@/_user/repositories/itinerary-repository";
import { KycRepository } from "@/_user/repositories/kyc-repository";
import { UserRepository } from "@/_user/repositories/user-repository";
import { Abi__factory } from "@/lib/types/contract";
import { AppError } from "@/utils/error";
import { contract, createHash } from "@/utils/ethers";
import { generateQr } from "@/utils/qr-generator";

export class ItineraryService {
  private kycRepository: KycRepository;
  private itineraryRepository: ItineraryRepository;
  private userRepository: UserRepository;

  constructor() {
    this.kycRepository = new KycRepository();
    this.itineraryRepository = new ItineraryRepository();
    this.userRepository = new UserRepository();
  }

  async register(data: RegisterItineraryDTOType, userId: string) {
    const kycId = await this.kycRepository.getIdByUserId(userId);
    const itineraryId = await this.itineraryRepository.save(
      data,
      userId,
      kycId
    );
    await this.userRepository.setOnboardingStatus(userId, 3);
    await this.userRepository.setItineraryId(userId, itineraryId);
    await this.generateTouristId(userId);
  }

  private async generateTouristId(userId: string) {
    const { kycDetails, itineraryDetails, emergencyDetails } =
      await this.userRepository.getDetailsForHash(userId);
    if (!kycDetails || !itineraryDetails || !emergencyDetails)
      throw new AppError(400, "Incomplete KYC, Itinerary or Emergency details");
    const startTimestamp = Math.floor(
      new Date(itineraryDetails.arrivalDate).getTime() / 1000
    );
    const endTimestamp = Math.floor(
      new Date(itineraryDetails.departureDate).getTime() / 1000
    );

    const tx = await contract.issueTouristId(
      createHash(JSON.stringify(kycDetails)),
      createHash(JSON.stringify(itineraryDetails)),
      createHash(JSON.stringify(emergencyDetails)),
      startTimestamp,
      endTimestamp
    );

    const receipt = await tx.wait();

    if (!receipt) throw new AppError(500, "Transaction failed");

    const iface = Abi__factory.createInterface();

    const event = receipt.logs
      .map((log) => {
        try {
          return iface.parseLog(log);
        } catch {
          return null;
        }
      })
      .find((log) => log && log.name === "TouristIdIssued");

    if (!event)
      throw new AppError(500, "TouristIdIssued event not found in logs");

    const touristId = event.args.touristId as string;
    const issuer = event.args.issuer as string;
    const qrCode = await generateQr(touristId);

    await this.userRepository.setTouristId(userId, touristId);
    await this.userRepository.setQrcode(userId, qrCode);
  }
}
