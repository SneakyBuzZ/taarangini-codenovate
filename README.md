# Triana – SafeJourney Navigation System

**Team:** Taarangini  
**Hackathon:** Codenovate 25  
**Problem Statement:** SafeJourney  
**Project Title:** Triana

---

## 📝 Problem Overview

Traveling alone at night often feels unsafe due to:

- Poorly lit or deserted areas
- High-crime zones
- Accident-prone locations
- Routes previously reported as dangerous
- Lack of real-time awareness about surroundings

Currently, there is _no reliable system_ that helps individuals choose the **safest route** to their destination based on **live, dynamic conditions**.

**Triana** solves this by providing a platform that:

- Identifies danger zones in real time
- Calculates safety-weighted routes
- Alerts users about risky areas ahead
- Helps travelers navigate confidently at night

---

## 📁 Repository Structure

This repository is organized as a **monorepo**, containing all major components of the system.

```bash
/
├── android/ # Native Android mobile application
├── server/ # Node.js backend (REST APIs, logic)
├── web/ # Next.js web application (Frontend dashboard)
├── wss/ # WebSocket server for real-time events
└── contract/ # Smart contracts written in Solidity
```

## ⚙️ Modules

### **`android/` – Native Android Application**

- Main user app for safe navigation
- Real-time warnings and route guidance
- Built for devices used during travel
- Developed using Android Studio

### **`server/` – Node.js Backend**

- Core API server
- Safety scoring algorithms
- User auth & session management
- Database integration

### **`web/` – Next.js Web Application**

- User dashboards
- Route visualization UI
- Admin controls & analytics
- Built with Next.js, Tailwind, TypeScript

### **`wss/` – Real-Time WebSocket Server**

- Live location tracking
- Real-time alerts (danger ahead, accident updates)
- Push notifications to clients

### **`contract/` – Blockchain Smart Contracts**

- Solidity contracts
- Logging important safety events
- Ensuring tamper-resistant audit trails

## 🛠 Development Notes

- Each module runs independently.
- You can open specific folders in different IDEs:
  - **VS Code** → `web/`, `server/`, `wss/`, `contract/`
  - **Android Studio** → `android/`
- Each folder contains its own setup instructions and dependencies.
- This outer README only serves as a **top-level overview** of the project.

## 📞 Contact

For hackathon communication — Team Taarangini.
