import jwt from "jsonwebtoken";
import { JWT_SECRET } from "@/utils/constants";
import { generateHash } from "@/utils/bcrypt";

export async function generateToken(userId: string) {
  const token = jwt.sign({ userId }, JWT_SECRET, {
    expiresIn: "1y",
  });

  const hashedToken = await generateHash(token);
  return { token, hashedToken };
}

export function verifyToken(token: string) {
  return jwt.verify(token, JWT_SECRET);
}
