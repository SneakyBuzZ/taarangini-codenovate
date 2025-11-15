"use client";

import {
  SidebarHeader,
  SidebarMenu,
  SidebarMenuItem,
  SidebarMenuButton,
  SidebarContent,
} from "@/components/ui/sidebar";
import {
  LayoutDashboard,
  FileText,
  Map,
  ShieldAlert,
  History,
  Contact,
  Shield,
} from "lucide-react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { Badge } from "../ui/badge";

const menuItems = [
  {
    href: "/",
    label: "Dashboard",
    icon: LayoutDashboard,
    gradient: "from-blue-500 to-blue-600",
  },
  {
    href: "/map",
    label: "Interactive Map",
    icon: Map,
    gradient: "from-emerald-500 to-emerald-600",
  },
  {
    href: "/digital-id",
    label: "Digital ID",
    icon: Contact,
    gradient: "from-indigo-500 to-indigo-600",
  },
  {
    href: "/e-fir",
    label: "Trip Analytics",
    icon: FileText,
    gradient: "from-amber-500 to-amber-600",
  },
  {
    href: "/incidents",
    label: "Incident Reporting",
    icon: ShieldAlert,
    gradient: "from-rose-500 to-rose-600",
  },
  {
    href: "/alerts",
    label: "Alert History",
    icon: History,
    badge: "3",
    gradient: "from-yellow-500 to-yellow-600",
  },
];

export default function AppSidebar() {
  const pathname = usePathname();

  return (
    <div className="flex flex-col h-full">
      <SidebarHeader className="p-6">
        <Link
          href="/"
          className="flex items-center gap-3 hover:opacity-80 transition-opacity cursor-pointer"
        >
          <div className="p-2.5 rounded-2xl bg-gradient-to-br from-blue-500 via-indigo-500 to-blue-600 shadow-lg">
            <Shield className="h-6 w-6 text-white" />
          </div>
          <div className="flex flex-col min-w-0 flex-1">
            <h1 className="font-headline text-base font-bold gradient-text whitespace-nowrap">
              Traiana Guardian
            </h1>
          </div>
        </Link>
      </SidebarHeader>
      <SidebarContent className="flex-1 p-4">
        <div className="space-y-2">
          <p className="text-xs font-semibold text-gray-400 uppercase tracking-wider px-3 mb-3">
            Main Navigation
          </p>

          <SidebarMenu>
            {menuItems.map((item) => {
              const isActive = pathname === item.href;
              const Icon = item.icon;

              return (
                <SidebarMenuItem key={item.href} className="mb-1">
                  <SidebarMenuButton
                    asChild
                    className={`
                      group relative rounded-xl transition-all duration-300 hover:scale-[1.02]
                      ${
                        isActive
                          ? "bg-white/10 backdrop-blur-sm border border-white/20 shadow-lg"
                          : "hover:bg-white/5"
                      }
                    `}
                  >
                    <Link
                      href={item.href}
                      className="flex items-center gap-3 p-3"
                    >
                      <div
                        className={`
                        p-2 rounded-lg bg-gradient-to-r ${
                          item.gradient
                        } shadow-md
                        ${isActive ? "shadow-lg" : "group-hover:scale-110"}
                        transition-all duration-300
                      `}
                      >
                        <Icon className="h-4 w-4 text-white" />
                      </div>
                      <div className="flex items-center justify-between flex-1">
                        <span
                          className={`
                          font-medium transition-colors
                          ${
                            isActive
                              ? "text-white"
                              : "text-gray-300 group-hover:text-gray-100"
                          }
                        `}
                        >
                          {item.label}
                        </span>
                        {item.badge && (
                          <Badge className="bg-gradient-to-r from-blue-500 to-indigo-500 text-white text-xs px-2 py-0.5 shadow-md">
                            {item.badge}
                          </Badge>
                        )}
                      </div>
                      {isActive && (
                        <div className="absolute right-0 top-1/2 -translate-y-1/2 w-1 h-8 bg-gradient-to-b from-indigo-500 to-purple-500 rounded-l-full" />
                      )}
                    </Link>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              );
            })}
          </SidebarMenu>
        </div>
      </SidebarContent>
    </div>
  );
}
