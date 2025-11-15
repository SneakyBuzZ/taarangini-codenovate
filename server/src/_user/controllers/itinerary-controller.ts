import { ItineraryService } from "@/_user/services/itinerary-service";
import { DataResponse } from "@/utils/response";
import { Request, Response } from "express";

class ItineraryController {
  private itineraryService: ItineraryService;

  constructor() {
    this.itineraryService = new ItineraryService();
  }

  register = async (req: Request, res: Response) => {
    const userId = req.user?.id;
    if (!userId) {
      throw new Error("Unauthorized");
    }
    await this.itineraryService.register(req.body, userId);
    res
      .status(201)
      .json(new DataResponse(201, {}, "Itinerary registration successful"));
  };
}

export default ItineraryController;
