import "dotenv/config";
import Socket from "@/socket";

const PORT = process.env.PORT ? parseInt(process.env.PORT, 10) : 3001;

new Socket(PORT);
