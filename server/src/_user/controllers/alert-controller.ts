import { Request, Response } from "express";
import { AlertService } from "@/_user/services/alert-service";
import { DataResponse } from "@/utils/response";
import { AppError } from "@/utils/error";
import type { NearestAlertsDTOType, SafetyMetricsDTOType } from "@/_user/dto";
import { ObjectId } from "mongodb";

class AlertController {
  private alertService: AlertService;
  constructor() {
    this.alertService = new AlertService();
  }
  register = async (req: Request, res: Response) => {
    //Kept the userId optional because anyone can report the incident
    const userId = req.user?.id;
    await this.alertService.register(req.body, userId);
    res
      .status(201)
      .json(new DataResponse(201, {}, "Alert registration successful"));
  };

  getById = async (req: Request, res: Response) => {
    const { id } = req.params;
    if (!id) throw new AppError(400, "Id required");
    const alert = await this.alertService.getById(id);
    if (!alert) throw new AppError(404, "Alert not found");
    res.status(200).json(new DataResponse(200, alert, "Alert fetched"));
  };

  // POST /api/alert/nearby -> body: { latitude, longitude, maxDistanceKm?, limit? }
  getNearestAlerts = async (req: Request, res: Response) => {
    const body = req.body as NearestAlertsDTOType;
    // basic validation is handled by validateData middleware
    const alerts = await this.alertService.getNearestAlerts(body);
    res.status(200).json(new DataResponse(200, alerts, "Nearest alerts"));
  };

  // new: compute safety for an alert and persist it
  computeSafety = async (req: Request, res: Response) => {
    const { id } = req.params;
    if (!id || !ObjectId.isValid(id)) throw new AppError(400, "Invalid id");
    const metrics = req.body as SafetyMetricsDTOType;
    const safety = await this.alertService.computeSafety(id, metrics);
    if (!safety) throw new AppError(500, "Failed to compute safety");
    res.status(200).json(new DataResponse(200, safety, "Safety computed"));
  };
}

export default AlertController;
