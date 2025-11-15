import mongoose from "mongoose";

const alertsSchema = new mongoose.Schema(
  {
    image: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      required: true,
      trim: true,
    },
    description: {
      type: String,
      required: true,
      trim: true,
    },
    location: {
      type: String,
      required: true,
    },
    severity: {
      type: Number,
      required: true,
    },
    categoryChip: {
      type: [String],
      enum: ["Weather", "Crime", "Health", "Transport"],
      required: true,
    },

    // This field describes which user or admin reported the incident
    reportedBy: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "User",
      required: false,
    },

    //GeoJSON
    coordinates: {
      type: {
        type: String,
        enum: ["Point"],
        default: "Point",
      },
      coordinates: {
        type: [Number], // [longitude, latitude]
        required: true,
      },
    },
  },
  { timestamps: true }
);
alertsSchema.index({ coordinates: "2dsphere" }); //allows you to use mongodb's geospatial index

export default mongoose.model("Alerts", alertsSchema);
