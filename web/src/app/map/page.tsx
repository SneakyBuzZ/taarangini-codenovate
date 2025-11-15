"use client";

import { useEffect, useState } from "react";
import OpenLayerMap from "@/components/map/OpenLayerMap";

export default function MapPage() {
  const [mapCenter, setMapCenter] = useState<[number, number] | null>(null);

  // Alert points coordinates: [lon, lat]
  const alertPoints: [number, number][] = [
    [75.4352, 17.4106],
    [78.42229, 17.41859],
  ];

  useEffect(() => {
    setMapCenter([78.4652, 17.4106]);
  }, []);

  return (
    <div className="h-screen w-full relative overflow-hidden">
      <div className="absolute inset-0 bg-gradient-to-br from-slate-900 via-slate-800 to-slate-700">
        <div className="absolute inset-0 bg-[radial-gradient(circle_at_50%_50%,rgba(59,130,246,0.1)_0px,transparent_50%)] opacity-60"></div>
      </div>
      <div className="absolute inset-0 z-10">
        <OpenLayerMap mapCenter={mapCenter} />
      </div>
    </div>
  );
}
