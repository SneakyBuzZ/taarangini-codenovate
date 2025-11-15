import mongoose from "mongoose";

const locationSchema = new mongoose.Schema(
  {
    city: {
      type: String,
      required: true,
      trim: true,
    },
    stay: {
      type: String,
      required: false,
      trim: true,
    },
    fromDate: {
      type: Date,
      required: true,
    },
    toDate: {
      type: Date,
      required: true,
    },
  },
  { _id: false }
);

const itinerarySchema = new mongoose.Schema(
  {
    kycId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Kyc",
      required: true,
    },
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "User",
      required: true,
    },
    arrivalDate: {
      type: Date,
      required: true,
    },
    departureDate: {
      type: Date,
      required: true,
    },
    locations: {
      type: [locationSchema],
      required: true,
    },
    transportMode: {
      type: [String],
      enum: ["flight", "train", "car", "bus", "ship", "other"],
      required: true,
    },
  },
  { timestamps: true }
);

itinerarySchema.index({ kycId: 1 });
itinerarySchema.index({ arrivalDate: 1, departureDate: 1 });

export default mongoose.model("Itinerary", itinerarySchema);
