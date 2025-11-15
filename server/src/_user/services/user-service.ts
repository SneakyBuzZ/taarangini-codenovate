import { UserRepository } from "@/_user/repositories/user-repository";
import { RegisterUserDTOType } from "@/_user/dto";
import { AppError } from "@/utils/error";
import { generateToken } from "@/utils/jwt";

export class UserService {
  private userRepository: UserRepository;

  constructor() {
    this.userRepository = new UserRepository();
  }

  async register(data: RegisterUserDTOType) {
    const existedUser = await this.userRepository.findByEmail(data.email);
    if (existedUser) {
      throw new AppError(403, "User already exists");
    }
    const userId = await this.userRepository.save(data);
    const { token, hashedToken } = await generateToken(userId);
    await this.userRepository.setToken(userId, hashedToken);
    await this.userRepository.setOnboardingStatus(userId, 1);
    return token;
  }

  async getAllUsers() {
    return await this.userRepository.getAll();
  }

  async getUserById(id: string) {
    return await this.userRepository.findById(id);
  }

  async updateUser(id: string, update: any) {
    return await this.userRepository.update(id, update);
  }

  async deleteUser(id: string) {
    return await this.userRepository.delete(id);
  }
}
