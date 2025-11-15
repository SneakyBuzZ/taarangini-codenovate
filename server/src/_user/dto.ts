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
