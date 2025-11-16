import { Router } from "express";
import AlertController from "@/_user/controllers/alert-controller";
import { authenticateJwt } from "@/middlewares/authenticate-middleware";
import { validateData } from "@/middlewares/validate-middleware";
import { RegisterAlertDTO, NearestAlertsDTO, SafetyMetricsDTO } from "../dto";
import { catchAsync } from "@/utils/catch-async";

const alertRouter = Router();
const alertController = new AlertController();

alertRouter.post(
  "/",
  //   authenticateJwt(), --> Commented it temporarily because anonymous reports are welcome
  validateData(RegisterAlertDTO),
  catchAsync(alertController.register)
);

alertRouter.post(
  "/nearby",
  validateData(NearestAlertsDTO),
  catchAsync(alertController.getNearestAlerts)
);

alertRouter.get("/:id", catchAsync(alertController.getById));

// new: compute safety for a specific alert (body = normalized metrics 0..1)
alertRouter.post(
  "/:id/safety",
  validateData(SafetyMetricsDTO),
  catchAsync(alertController.computeSafety)
);

export default alertRouter;
