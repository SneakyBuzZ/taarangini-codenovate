import { JWT_SECRET } from "@/utils/constants";
import jwt from "jsonwebtoken";

export function verifyToken(token: string): { userId: string } {
  const decoded = jwt.verify(token, JWT_SECRET) as { userId: string };
  return decoded;
}
