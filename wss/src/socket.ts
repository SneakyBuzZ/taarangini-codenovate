import { WebSocket, WebSocketServer } from "ws";
import { handleAuth } from "@/handlers/auth-handler";
import { Message, SosContent } from "./utils/types";

class Socket {
  private wss: WebSocketServer;
  private clients: Map<string, WebSocket>;
  constructor(port: number) {
    this.wss = new WebSocketServer({ port });
    this.init();
    this.clients = new Map<string, WebSocket>();
  }

  private init() {
    console.log("WEB SOCKET SERVER STARTED ON PORT - ", this.wss.options.port);

    this.wss.on("connection", (ws, req) => {
      const decoded = handleAuth(req, ws);
      if (!decoded || !decoded.userId) return;

      const userId = decoded.userId;
      console.log(`USER CONNECTED - ${userId}`);

      this.clients.set(userId, ws);

      ws.on("message", (ioMessage) => {
        const message: Message<SosContent> = JSON.parse(ioMessage.toString());
        this.broadcast(JSON.stringify(message), ws);
        console.log(
          "BROADCASTED: ",
          message.content.location.latitude,
          " | ",
          message.content.location.longitude
        );
      });

      ws.on("close", () => {
        console.log("CLIENT DISCONNECTED - ", userId);
        this.clients.delete(userId);
      });
    });
  }

  private broadcast(data: string, ws: WebSocket) {
    this.wss.clients.forEach((client) => {
      if (client.readyState === client.OPEN && client != ws) {
        client.send(data);
      }
    });
  }
}

export default Socket;
