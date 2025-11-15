import mongoose from "mongoose";
import { DATABASE_URL } from "@/utils/constants";
import { AppError } from "./error";

export async function connectDB() {
  try {
    if (!DATABASE_URL) {
      throw new AppError(500, "Database URL is not defined");
    }
    await mongoose.connect(DATABASE_URL);
    console.log("Connected to MongoDB");
  } catch (error) {
    console.error("MongoDB connection error:", error);
    process.exit(1);
  }
}
