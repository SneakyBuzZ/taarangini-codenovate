import { Router } from "express";
import AlertController from "@/_user/controllers/alert-controller";
import { authenticateJwt } from "@/middlewares/authenticate-middleware";
import { validateData } from "@/middlewares/validate-middleware";
import { RegisterAlertDTO, NearestAlertsDTO } from "../dto";
import { catchAsync } from "@/utils/catch-async";

const alertRouter = Router();
const alertController = new AlertController();

alertRouter.post(
  "/",
  //   authenticateJwt(), --> Commented it temporarily because anonymous reports are welcome
  validateData(RegisterAlertDTO),
  catchAsync(alertController.register)
);

alertRouter.get(
  "/",
  validateData(NearestAlertsDTO),
  catchAsync(alertController.getNearestAlerts)
);

alertRouter.get("/:id", catchAsync(alertController.getById));

export default alertRouter;
