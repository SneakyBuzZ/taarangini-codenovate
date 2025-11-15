# Traiana Server

A Node.js backend server built with Express, TypeScript, and modular architecture for user management, KYC, and itinerary features.

## 🪶 Features

- User registration, authentication, and management
- KYC (Know Your Customer) registration and validation
- Itinerary creation and management
- Modular structure with controllers, services, repositories, and models
- Middleware for authentication, error handling, and validation
- Secure cookie handling and JWT-based authentication
- Environment variable support via dotenv
- CORS configuration for client-server communication
- Project Structure
- Getting Started
- Prerequisites
- Node.js (v18+ recommended)
  npm or yarn
  Installation
  Environment Variables
  Create a .env file in the server directory with the following variables:

## 💽 Running the Server

- **Open folder and Install Dependencies**

```bash
cd server
npm install
```

- **Create a .env file**

```env
PORT=5000
DATABASE_URL=
JWT_SECRET=
COOKIE_SECRET=
CLIENT_URL=
```

The server will start on http://localhost:PORT.

## 🛜 API Endpoints

- GET /api/user - Get all users

- POST /api/user/register - Register a new user

- GET /api/user/auth-status - Check authentication status

- GET /api/user/:id - Get user by ID

- PUT /api/user/:id - Update user

- DELETE /api/user/:id - Delete user

- POST /api/kyc/register - Register KYC for a user

- POST /api/itinerary/register - Register an itinerary

## 🧑‍💻 Development

- TypeScript for type safety
- Morgan for logging
- Cookie-parser for secure cookies
- CORS for cross-origin requests
- License
  MIT

## 👥 Team

**Team Taarangini – Codenovate 25**
