import { z } from "zod";

// * -------------- USER ----------------- *

export const RegisterUserDTO = z.object({
  fullname: z.string().min(10).max(100),
  gender: z.enum(["male", "female", "other"]),
  dob: z.coerce.date(),
  email: z.string().email(),
  mobile: z.string().length(10),
  bloodType: z.string().min(1),
  nationality: z.string().min(2).max(56),
  allergies: z.array(z.string()).optional(),
  medicalConditions: z.array(z.string()).optional(),
});

export type RegisterUserDTOType = z.infer<typeof RegisterUserDTO>;

// Update DTO (for PUT /api/user/:id)
export const UpdateUserDTO = RegisterUserDTO.partial();
export type UpdateUserDTOType = z.infer<typeof UpdateUserDTO>;

// UserId DTO (for param validation)
export const UserIdDTO = z.object({
  id: z.string().length(24), // MongoDB ObjectId
});
export type UserIdDTOType = z.infer<typeof UserIdDTO>;

// * -------------- KYC ----------------- *

export const RegisterKycDTO = z.object({
  docType: z.enum(["passport", "aadhaar", "driver_license"]),
  docNumber: z.string().min(3).max(50),
  docImage: z.string().url(),
  issuedAt: z.enum(["goi", "uidai", "mea", "rta", "fpa", "other"]),
  address: z.string().min(10).max(200),
  emergencyContact: z.object({
    name: z.string().min(2).max(100),
    relation: z.enum([
      "parent",
      "spouse",
      "sibling",
      "friend",
      "guardian",
      "other",
    ]),
    phone: z.string().length(10),
  }),
});
export type RegisterKycDTOType = z.infer<typeof RegisterKycDTO>;

// * -------------- ITINERARY ----------------- *

export const RegisterItineraryDTO = z.object({
  arrivalDate: z.coerce.date(),
  departureDate: z.coerce.date(),
  locations: z.array(
    z.object({
      city: z.string().min(2).max(100),
      stay: z.string().min(2).max(200).optional(),
      fromDate: z.coerce.date(),
      toDate: z.coerce.date(),
    })
  ),
  transportMode: z
    .array(z.enum(["flight", "train", "car", "bus", "ship", "other"]))
    .min(1),
});
export type RegisterItineraryDTOType = z.infer<typeof RegisterItineraryDTO>;

// --- ALERT ---
export const RegisterAlertDTO = z.object({
  image: z.string().url(),
  title: z.string().min(2).max(100),
  description: z.string().min(5).max(1000),
  location: z.string().min(3).max(200),
  severity: z.number().min(0).max(10),
  categoryChip: z.array(
    z.enum([
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
    ])
  ),
  // GeoJSON Point: [longitude, latitude]
  coordinates: z.object({
    type: z.literal("Point"),
    coordinates: z
      .tuple([z.number(), z.number()])
      .refine(
        (val) =>
          val[0] >= -180 && val[0] <= 180 && val[1] >= -90 && val[1] <= 90,
        "Invalid longitude/latitude range"
      ),
  }),
});
export type RegisterAlertDTOType = z.infer<typeof RegisterAlertDTO>;

// Safety metrics DTO (normalized values 0..1) — add this, do NOT remove RegisterAlertDTO
export const SafetyMetricsDTO = z.object({
  crime: z.number().min(0).max(1).optional(),
  accident: z.number().min(0).max(1).optional(),
  response: z.number().min(0).max(1).optional(),
  sos: z.number().min(0).max(1).optional(),
  lighting: z.number().min(0).max(1).optional(),
  cctv: z.number().min(0).max(1).optional(),
  pedestrian: z.number().min(0).max(1).optional(),
  // optional weight overrides (0..1)
  wc: z.number().min(0).max(1).optional(),
  wa: z.number().min(0).max(1).optional(),
  wl: z.number().min(0).max(1).optional(),
  wv: z.number().min(0).max(1).optional(),
  wp: z.number().min(0).max(1).optional(),
  wr: z.number().min(0).max(1).optional(),
  ws: z.number().min(0).max(1).optional(),
});
export type SafetyMetricsDTOType = z.infer<typeof SafetyMetricsDTO>;

// DTO for fetching nearest alerts
export const NearestAlertsDTO = z.object({
  latitude: z
    .number()
    .refine((v) => v >= -90 && v <= 90, "Invalid latitude range"),
  longitude: z
    .number()
    .refine((v) => v >= -180 && v <= 180, "Invalid longitude range"),
  maxDistanceKm: z.number().min(0).max(30).optional(),
  limit: z.number().min(1).max(5).optional(),
});

export type NearestAlertsDTOType = z.infer<typeof NearestAlertsDTO>;
