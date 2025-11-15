import { ErrorResponse } from "@/utils/response";
import { JWT_SECRET } from "@/utils/constants";
import jwt from "jsonwebtoken";
import User from "@/_user/models/user-model";
import { AppError } from "@/utils/error";

export const authenticateJwt = () => {
  return async (req: any, res: any, next: any) => {
    const token = req.headers["authorization"]?.split(" ")[1];

    if (!token) {
      return res.status(401).json(new ErrorResponse(401, "Unauthorized"));
    }

    try {
      const decodedToken = jwt.verify(token, JWT_SECRET) as { userId: string };
      const userId = decodedToken.userId;
      const user = await User.findById(userId).select("_id");
      if (!user) throw new AppError(401, "Unauthorized");
      req.user = {
        id: user._id,
      };
      next();
    } catch (error: any) {
      if (error.name === "TokenExpiredError") {
        return res
          .status(401)
          .json(new ErrorResponse(401, "Access token expired"));
      }

      return res.status(401).json(new ErrorResponse(401, "Unauthorized"));
    }
  };
};
