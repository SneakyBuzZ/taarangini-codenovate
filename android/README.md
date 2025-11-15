# Triana Android App

This is the **Android application** for **Triana**, a real-time safe navigation system built for the **SafeJourney** problem statement at **Codenovate 25** by **Team Taarangini**.

The Android app is the primary interface for users traveling at night, helping them choose safer routes, receive alerts, and track real-time danger indicators.

## 📱 Features

- 🔒 **Safe Route Navigation**  
  Provides route suggestions based on safety scores and live data.

- ⚠️ **Real-Time Alerts**  
  Alerts users about nearby danger zones, accident-prone areas, and risky surroundings.

- 🛰️ **Live Location Tracking**  
  Tracks user location to deliver dynamic updates.

- 🌙 **Night Mode Optimized**  
  Designed for nighttime use with visibility-focused UI.

- 🔗 **Backend Integration**  
  Connects with the Node.js REST API and WebSocket server.

- 📡 **Real-time Events (WSS)**  
  Receives safety updates through the WebSocket server.

## 🛠️ Tech Stack

- **Language:** Kotlin
- **IDE:** Android Studio
- **Architecture:** MVVM
- **Network:** Retrofit / WebSockets
- **Location:** Native Android Location Services
- **Maps:** Mapbox SDK
- **Permissions:** Runtime permission handling for location
- **Coroutines + Flow** for async operations
- **Jetpack Components:** LiveData, ViewModel, Navigation

## 📂 Project Structure

## 📂 Android Project Structure

```bash
/
├── components/ # UI components and Jetpack Compose elements
├── screens/ # All mobile screens / UI pages
├── lib/ # Core app logic and business layer
├── utils/ # Utility and helper functions
└── MainActivity.kt # Entry point of the Android application
```

### 1. `MainActivity.kt`

- Entry point of the Android application
- Hosts the navigation graph and initializes core modules

---

### 2. `screens/`

- Contains all **mobile screens / UI pages**
- Each screen represents a full-screen user interaction
- Example: HomeScreen, MapScreen, AlertScreen, LoginScreen

---

### 3. `components/`

- Reusable **UI components** and Jetpack Compose elements
- Used across multiple screens
- Example: Buttons, Cards, Dialogs, Loaders

---

### 4. `utils/`

- Utility and helper functions
- Common logic reused throughout the app
- Example: permission helpers, date/time utils, loggers

---

### 5. `lib/`

- Core app logic and business layer
- Contains APIs, repositories, ViewModels, data handlers
- Example: `ApiService`, `UserViewModel`, `Repository`, `WebSocketClient`

---

## 🚀 Getting Started

### **1. Open the Project**

Open ONLY the `android/` folder in Android Studio:

_Do not open the entire monorepo — only this folder._

### **2. Requirements**

- Android Studio **Giraffe+**
- Android Device or Emulator
- Minimum SDK: **21+**
- Google Maps API key (add to `local.properties`)

### **3. Setup Environment Variables**

Create/add in `local.properties`:

```.env
sdk.dir=C\:\\Users\\<user>\\AppData\\Local\\Android\\Sdk
API_URL=
SOCKET_URL=
```

## ▶️ Run the App

1. Connect a device (or start emulator)
2. Press **Run (▶)** in Android Studio

The app will build and launch with live data.

## 🔌 API Configuration

The app depends on:

- **server/** → REST API (Node.js)
- **wss/** → Real-time WebSocket alerts

Ensure backend services are running when testing real-time features.

## 👥 Team

**Team Taarangini – Codenovate 25**
