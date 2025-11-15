import mongoose from "mongoose";

const userSchema = new mongoose.Schema(
  {
    fullname: {
      type: String,
      required: true,
    },
    gender: {
      type: String,
      enum: ["male", "female", "other"],
      required: true,
    },
    dob: {
      type: Date,
      required: true,
    },
    email: {
      type: String,
      required: false,
      unique: true,
    },
    mobile: {
      type: String,
      required: true,
      unique: true,
    },
    bloodType: {
      type: String,
      required: false,
    },
    nationality: {
      type: String,
      required: true,
    },
    allergies: [
      {
        type: String,
        required: false,
        default: "none",
      },
    ],
    medicalConditions: [
      {
        type: String,
        required: false,
        default: "none",
      },
    ],
    hashedToken: {
      type: String,
      required: false,
    },
    onboardingStatus: {
      type: Number,
      enum: [0, 1, 2, 3],
      default: 0,
    },
    touristId: {
      type: String,
      required: false,
      unique: true,
    },
    kycId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Kyc",
      required: false,
    },
    itineraryId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Itinerary",
      required: false,
    },
    qrcode: {
      type: String,
      required: false,
      unique: true,
    },
  },
  { timestamps: true }
);

userSchema.index({ nationality: 1 });

export default mongoose.model("User", userSchema);
