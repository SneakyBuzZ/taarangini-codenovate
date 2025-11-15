import type { Metadata } from "next";
import "@/styles/globals.css";
import {
  SidebarProvider,
  Sidebar,
  SidebarInset,
} from "@/components/ui/sidebar";
import AppSidebar from "@/components/sidebar/app-sidebar";
import { Toaster } from "@/components/ui/toaster";
import { Inter } from "next/font/google";
import { cn } from "@/lib/utils";
import HeatMap from "@/components/dashboard/heat-map";
import SosAlertsList from "@/components/sos-alerts/sos-alerts-list";

export const metadata: Metadata = {
  title: "Traiana Guardian",
  description:
    "Advanced AI-powered tourist safety monitoring and incident response system for Traiana",
};

const fontSans = Inter({
  subsets: ["latin"],
  variable: "--font-sans",
});

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="dark" suppressHydrationWarning>
      <body
        className={cn(
          "min-h-screen bg-neutral-900 font-sans antialiased",
          fontSans.variable
        )}
      >
        <div className="relative flex min-h-screen">
          <SidebarProvider>
            <Sidebar className="border-r border-neutral-800 bg-neutral-950/50">
              <AppSidebar />
            </Sidebar>
            <SidebarInset className="flex-1 bg-neutral-900">
              <main className="animate-fade-in flex-1 flex overflow-hidden">
                {children}
                <SosAlertsList />
              </main>
            </SidebarInset>
          </SidebarProvider>
        </div>

        <Toaster />
      </body>
    </html>
  );
}
