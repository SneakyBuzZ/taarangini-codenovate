import { Router } from "express";
import ItineraryController from "@/_user/controllers/itinerary-controller";
import { authenticateJwt } from "@/middlewares/authenticate-middleware";
import { validateData } from "@/middlewares/validate-middleware";
import { RegisterItineraryDTO } from "../dto";
import { catchAsync } from "@/utils/catch-async";

const itineraryRouter = Router();
const itineraryController = new ItineraryController();

itineraryRouter.post(
  "/",
  authenticateJwt(),
  validateData(RegisterItineraryDTO),
  catchAsync(itineraryController.register)
);

export default itineraryRouter;
