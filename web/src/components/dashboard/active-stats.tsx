import React from "react";
import { Users, BarChart3 } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function ActiveStats() {
  return (
    <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4 w-full">
      <Card className="bg-slate-800/50 border-slate-700 shadow-xl hover:bg-slate-800/70 transition-all duration-300 backdrop-blur-sm">
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-3">
          <CardTitle className="text-sm font-semibold text-slate-200">
            Active Tourists
          </CardTitle>
          <div className="p-2 rounded-xl bg-gradient-to-r from-blue-500 to-blue-600 shadow-md">
            <Users className="h-4 w-4 text-white" />
          </div>
        </CardHeader>
        <CardContent>
          <div className="text-3xl font-bold text-white">12,543</div>
          <div className="flex items-center gap-1 mt-2">
            <div className="text-xs text-emerald-400 bg-emerald-500/20 px-2 py-1 rounded-lg font-semibold">
              +20.1%
            </div>
            <p className="text-xs text-slate-400">from last month</p>
          </div>
        </CardContent>
      </Card>

      <Card className="bg-slate-800/50 border-slate-700 shadow-xl hover:bg-slate-800/70 transition-all duration-300 backdrop-blur-sm">
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium text-slate-200">
            Incidents Reported
          </CardTitle>
          <BarChart3 className="h-4 w-4 text-indigo-400" />
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold text-white">27</div>
          <p className="text-xs text-slate-400">In the last 24 hours</p>
        </CardContent>
      </Card>
    </div>
  );
}
