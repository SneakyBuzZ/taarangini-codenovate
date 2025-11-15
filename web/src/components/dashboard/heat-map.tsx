import React from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

export default function HeatMap() {
  return (
    <Card className="col-span-4 bg-slate-800/50 border-slate-700 backdrop-blur-sm w-full">
      <CardHeader>
        <CardTitle className="font-headline text-white">
          Tourist Cluster Heatmap
        </CardTitle>
        <CardDescription className="text-slate-300">
          Real-time visualization of tourist density and high-risk zones.
        </CardDescription>
      </CardHeader>
      <CardContent className="pl-2">
        <div className="h-[450px] w-full relative">
          {/* CSS-only heatmap visualization */}
          <div className="absolute inset-0 bg-gradient-to-br from-slate-900 via-slate-800 to-slate-700 rounded-md border border-slate-600"></div>

          {/* Map grid pattern overlay */}
          <div
            className="absolute inset-0 opacity-30 rounded-md"
            style={{
              backgroundImage: `
                    linear-gradient(rgba(20, 184, 166, 0.2) 1px, transparent 1px),
                    linear-gradient(90deg, rgba(20, 184, 166, 0.2) 1px, transparent 1px)
                  `,
              backgroundSize: "40px 40px",
            }}
          ></div>

          {/* Heatmap hotspots */}
          <div className="absolute top-20 left-16 w-16 h-16 bg-red-500/60 rounded-full blur-lg animate-pulse"></div>
          <div className="absolute top-32 right-20 w-12 h-12 bg-yellow-500/50 rounded-full blur-md"></div>
          <div className="absolute bottom-24 left-32 w-20 h-20 bg-orange-500/40 rounded-full blur-lg animate-pulse delay-500"></div>
          <div className="absolute top-40 left-1/2 w-14 h-14 bg-red-600/70 rounded-full blur-md animate-pulse delay-1000"></div>
          <div className="absolute bottom-32 right-28 w-10 h-10 bg-yellow-400/45 rounded-full blur-sm"></div>
        </div>
      </CardContent>
    </Card>
  );
}
