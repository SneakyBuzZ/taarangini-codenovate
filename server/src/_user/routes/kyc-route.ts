import { Router } from "express";
import { validateData } from "@/middlewares/validate-middleware";
import { catchAsync } from "@/utils/catch-async";
import { authenticateJwt } from "@/middlewares/authenticate-middleware";
import { RegisterKycDTO } from "@/_user/dto";
import KycController from "@/_user/controllers/kyc-controller";

const kycRouter = Router();
const kycController = new KycController();

kycRouter.post(
  "/",
  authenticateJwt(),
  validateData(RegisterKycDTO),
  catchAsync(kycController.register)
);

export default kycRouter;
