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
      enum: [
        "Weather",
        "Crime",
        "Health",
        "Transport",
        "Infrastructure",
        "Fire",
        "Accident",
        "Theft",
        "Assault",
        "Public Safety",
      ],
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

    // Safety computed info (new)
    safety: {
      score: { type: Number, min: 0, max: 100, required: false },
      components: {
        crime: { type: Number, min: 0, max: 1, default: 0 },
        accident: { type: Number, min: 0, max: 1, default: 0 },
        response: { type: Number, min: 0, max: 1, default: 0 },
        sos: { type: Number, min: 0, max: 1, default: 0 },
        lighting: { type: Number, min: 0, max: 1, default: 0 },
        cctv: { type: Number, min: 0, max: 1, default: 0 },
        pedestrian: { type: Number, min: 0, max: 1, default: 0 },
      },
      weights: {
        wc: { type: Number, default: 0.3 },
        wa: { type: Number, default: 0.15 },
        wl: { type: Number, default: 0.15 },
        wv: { type: Number, default: 0.1 },
        wp: { type: Number, default: 0.07 },
        wr: { type: Number, default: 0.08 },
        ws: { type: Number, default: 0.15 },
      },
      computedAt: { type: Date },
    },
  },
  { timestamps: true }
);
alertsSchema.index({ coordinates: "2dsphere" }); //allows you to use mongodb's geospatial index

export default mongoose.model("Alerts", alertsSchema);
