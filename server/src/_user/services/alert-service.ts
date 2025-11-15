import type { RegisterAlertDTO, NearestAlertsDTOType } from "@/_user/dto";
import { AlertRepository } from "@/_user/repositories/alert-repository";
export class AlertService {
  private alertRepository: AlertRepository;
  constructor() {
    this.alertRepository = new AlertRepository();
  }

  async register(data: RegisterAlertDTO, userId?: string) {
    const alertId = await this.alertRepository.save(data, userId);
    return alertId;
  }
  async getById(id: string) {
    return this.alertRepository.getById(id);
  }

  async getNearestAlerts(dto: NearestAlertsDTOType) {
    const maxDistanceMeters = (dto.maxDistanceKm ?? 30) * 1000;
    const limit = dto.limit ?? 5;
    return this.alertRepository.getNearestAlerts(
      dto.latitude,
      dto.longitude,
      maxDistanceMeters,
      limit
    );
  }
}
