# Traiana WebSocket Server

This project implements a WebSocket server using Node.js and TypeScript. It provides real-time communication capabilities for clients, allowing them to connect, send messages, and receive updates.

## Features

- WebSocket server using `ws` library
- JWT authentication for secure connections
- TypeScript for type safety and better development experience
- Environment variable management with `dotenv`

## Prerequisites

- Node.js (v14 or higher)
- npm (v6 or higher)
- A valid JWT secret for authentication

## Running the Server

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd wss
   npm install
   ```

2. Create a `.env` file in the root directory and add your JWT secret:

   ```env
   JWT_SECRET=your_jwt_secret
   PORT=3000
   ```

3. Start the server:

   ```bash
    npm start
   ```

4. The server will be running on `ws://localhost:3000` (or the port you specified).

## 👥 Team

**Team Taarangini – Codenovate 25**
