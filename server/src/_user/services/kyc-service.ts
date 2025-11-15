import { KycRepository } from "@/_user/repositories/kyc-repository";
import { RegisterKycDTOType } from "@/_user/dto";
import { UserRepository } from "@/_user/repositories/user-repository";

export class KycService {
  private kycRepository: KycRepository;
  private userRepository: UserRepository;

  constructor() {
    this.kycRepository = new KycRepository();
    this.userRepository = new UserRepository();
  }

  async register(data: RegisterKycDTOType, userId: string) {
    const kycId = await this.kycRepository.save(data, userId);
    await this.userRepository.setOnboardingStatus(userId, 2);
    await this.userRepository.setKycId(userId, kycId);
  }
}
