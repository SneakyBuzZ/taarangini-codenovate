import { DataResponse } from "@/utils/response";
import { Request, Response } from "express";
import { KycService } from "@/_user/services/kyc-service";
import { AppError } from "@/utils/error";

export class KycController {
  private kycService: KycService;

  constructor() {
    this.kycService = new KycService();
  }

  register = async (req: Request, res: Response) => {
    const userId = req.user?.id;
    if (!userId) {
      throw new AppError(401, "Unauthorized");
    }
    await this.kycService.register(req.body, userId);
    res
      .status(201)
      .json(new DataResponse(201, {}, "KYC registration successful"));
  };
}

export default KycController;
