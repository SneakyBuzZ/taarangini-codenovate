import qrcode from "qrcode";
import { AppError } from "./error";

export const generateQr = async (touristId: string) => {
  try {
    const webUrl = `http://localhost:3000/verify/${touristId}`;
    const url = await qrcode.toDataURL(webUrl);
    return url;
  } catch (error) {
    throw new AppError(500, "QR Code generation failed");
  }
};
