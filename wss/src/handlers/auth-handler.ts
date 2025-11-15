// auth-handler.ts
import { extractToken } from "@/utils/extract-token";
import { verifyToken } from "@/utils/jwt";
import { IncomingMessage } from "http";
import { WebSocket } from "ws";

export function handleAuth(
  req: IncomingMessage,
  ws: WebSocket
): { userId: string } | null {
  try {
    const token = extractToken(req, ws);
    const decoded = verifyToken(token);
    return decoded;
  } catch (err) {
    if (ws.readyState === WebSocket.OPEN) {
      ws.send(JSON.stringify({ error: "INVALID_TOKEN" }));
      ws.close(1008, "INVALID TOKEN");
    }
    return null;
  }
}
