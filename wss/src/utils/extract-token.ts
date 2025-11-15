import { IncomingMessage } from "http";
import { WebSocket } from "ws";

export const extractToken = (req: IncomingMessage, ws: WebSocket) => {
  const url = new URL(req.url || "", `http://${req.headers.host}`);
  const token = url.searchParams.get("token");

  if (!token) {
    ws.close(1008, "TOKEN MISSING");
    throw new Error("TOKEN MISSING");
  }

  return token;
};
