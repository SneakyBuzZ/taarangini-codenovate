# Triana Guardian Eye : Web Application 👁️‍🗨️

**Advanced AI-Powered Tourist Safety Platform**

A comprehensive tourist safety monitoring and incident response system designed specifically for Triana, featuring real-time tracking, automated E-FIR generation, and intelligent alert management.

## 🌟 Features

### 🏠 **Dashboard**

- Real-time tourist statistics and safety metrics
- Interactive incident tracking with visual status indicators
- Quick action cards for immediate response
- System health monitoring

### 🗺️ **Interactive Map**

- Live tourist location tracking with OpenLayers integration
- Geo-fence monitoring for high-risk areas
- CCTV camera overlay and surveillance points
- Heat map visualization of incident hotspots

### 🆔 **Digital ID Management**

- Tourist profile verification system
- Trip itinerary tracking and status monitoring
- Safety score calculation based on travel patterns
- Emergency contact management

### 📋 **Automated E-FIR Generation**

- AI-powered incident report processing using Google Genkit
- Natural language processing for extracting key information
- Automated missing person report generation
- Structured data output for law enforcement

### 🚨 **Incident Reporting**

- Streamlined incident submission workflow
- Real-time status updates and officer assignment
- Categorized incident types (missing person, theft, medical, etc.)
- Priority-based alert system

### 📊 **Alert History**

- Comprehensive alert log with filtering capabilities
- Geo-fence breach notifications
- SOS alert management
- Device connectivity monitoring

## 🛠️ Technology Stack

### **Frontend**

- **Next.js 14** - React framework with App Router
- **TypeScript** - Type-safe development
- **Tailwind CSS** - Utility-first styling
- **Shadcn/ui** - Modern component library
- **Lucide Icons** - Consistent iconography

### **Mapping & Geospatial**

- **OpenLayers** - Interactive map rendering
- **Geospatial APIs** - Location services and geocoding

### **AI & Automation**

- **Google Genkit** - AI workflow orchestration
- **Natural Language Processing** - Text analysis for E-FIR generation
- **Automated Report Generation** - Structured data extraction

### **State Management & Forms**

- **React Hook Form** - Form handling and validation
- **Zod** - Schema validation
- **React Hooks** - State management

## 🚀 Getting Started

### Prerequisites

- Node.js 18.x or higher
- npm or yarn package manager
- Git for version control

### Installation

1. **Clone the repository**

   ```bash
   git clone <repo-url>
   cd web
   ```

2. **Install dependencies**

   ```bash
   npm install
   ```

3. **Set up environment variables**

   ```bash
   SERVER_URL=<your-server-api-url>
   SOCKET_URL=<your-websocket-url>
   ```

   Configure your environment variables for AI services and API endpoints.

4. **Run the development server**

   ```bash
   npm run dev
   ```

## 📁 Project Structure

```
src/
├── ai/                     # AI workflows and Genkit integration
│   ├── genkit.ts          # Genkit configuration
│   └── flows/             # AI automation flows
│       └── automate-e-fir-generation.ts
├── app/                   # Next.js App Router pages
│   ├── alerts/           # Alert management
│   ├── digital-id/       # Tourist ID system
│   ├── e-fir/           # E-FIR generation
│   ├── incidents/        # Incident reporting
│   ├── map/             # Interactive mapping
│   └── layout.tsx       # Root layout
├── components/           # Reusable UI components
│   ├── ui/              # Shadcn/ui components
│   ├── app-sidebar.tsx  # Navigation sidebar
│   ├── e-fir-form.tsx   # E-FIR form component
│   └── openlayers-map.tsx # Map component
├── hooks/               # Custom React hooks
├── lib/                 # Utility functions and configurations
└── styles/              # Global styles and CSS
```

## 🎨 Design System

### **Components**

- Consistent card styling with gradient headers
- Interactive hover states and transitions
- Glassmorphism effects replaced with solid backgrounds
- Responsive grid layouts for all screen sizes

## 🔒 Security Features

- Type-safe development with TypeScript
- Input validation with Zod schemas
- Secure form handling and data processing
- Protected routes and access control

## 📋 Available Scripts

```bash
# Development
npm run dev          # Start development server
npm run build        # Build for production
npm run start        # Start production server

# Code Quality
npm run lint         # Run ESLint
npm run type-check   # TypeScript type checking

# Deployment
npm run deploy       # Deploy to production
```

## 👥 Team

**Team Taarangini – Codenovate 25**
