import mongoose from "mongoose";

const kycSchema = new mongoose.Schema(
  {
    userId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "User",
    },
    docType: {
      type: String,
      enum: ["passport", "aadhaar", "driver_license"],
      required: true,
    },
    docNumber: {
      type: String,
      required: true,
      unique: true,
    },
    docImage: {
      type: String,
      required: true,
    },
    issuedAt: {
      type: String,
      enum: ["goi", "uidai", "mea", "rta", "fpa", "other"],
    },
    address: {
      type: String,
      required: true,
    },
    emergencyContact: {
      name: {
        type: String,
        required: true,
        trim: true,
      },
      relation: {
        type: String,
        required: true,
        enum: ["parent", "spouse", "sibling", "friend", "guardian", "other"],
      },
      phone: {
        type: String,
        required: true,
      },
    },
    validUntil: {
      type: Date,
      required: false,
    },
    status: {
      type: String,
      enum: ["active", "expired"],
      default: "active",
    },
  },
  { timestamps: true }
);

kycSchema.index({ userId: 1 });
kycSchema.index({ docType: 1, docNumber: 1 }, { unique: true });
kycSchema.index({ status: 1 });
kycSchema.index({ validUntil: 1 }, { expireAfterSeconds: 0 });

//goi -> government of india
//uidai -> unique identification authority of india
//mea -> ministry of external affairs
//rta -> road transport authority
//fpa -> foreign passport authority

export default mongoose.model("Kyc", kycSchema);
