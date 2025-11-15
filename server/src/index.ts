import express from "express";
import "dotenv/config";
import morgan from "morgan";
import cookieParser from "cookie-parser";
import userRouter from "@/_user/routes/user-route";
import { COOKIE_SECRET, PORT } from "@/utils/constants";
import cors from "cors";
import { errorMiddleware } from "@/middlewares/error-middleware";
import { connectDB } from "@/utils/db";
import kycRouter from "@/_user/routes/kyc-route";
import itineraryRouter from "@/_user/routes/itinerary-route";

const app = express();

app.use(cookieParser(COOKIE_SECRET));
app.use(cors({ origin: "*", credentials: true }));
app.use(express.json({ limit: "16kb" }));
app.use(express.urlencoded({ extended: true, limit: "16kb" }));
app.use(morgan("dev"));

app.get("/", (_, res) => {
  res.send("Hello World!");
});

app.use("/api/user", userRouter);
app.use("/api/kyc", kycRouter);
app.use("/api/itinerary", itineraryRouter);
app.use(errorMiddleware);

connectDB();

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
