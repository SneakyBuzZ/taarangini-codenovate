"use client";

import { useEffect, useRef, useState } from "react";
import Map from "ol/Map";
import View from "ol/View";
import TileLayer from "ol/layer/Tile";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import OSM from "ol/source/OSM";
import { fromLonLat } from "ol/proj";
import Feature from "ol/Feature";
import Point from "ol/geom/Point";
import { Style, Circle as CircleStyle, Fill, Stroke } from "ol/style";
import { SOCKET_URL, TOKEN } from "@/utils/constants";
import useWebSocket from "react-use-websocket";
import { Message, SosContent } from "@/lib/types/wss-message";

interface OpenLayerMapProps {
  mapCenter: [number, number] | null;
}

export default function OpenLayerMap({ mapCenter }: OpenLayerMapProps) {
  const mapRef = useRef<HTMLDivElement>(null);
  const mapInstance = useRef<Map | null>(null);
  const alertLayerRef = useRef<VectorLayer<VectorSource> | null>(null);

  // This will store [lon, lat] of all active SOS alerts
  const [alertPoints, setAlertPoints] = useState<[number, number][]>([
    // [78.46, 17.414],
    // [78.453, 17.412],
    // [78.423, 17.402],
  ]);

  const wsUrl = `${SOCKET_URL}?token=${TOKEN}`;
  const { lastJsonMessage } = useWebSocket(wsUrl);

  // Add new SOS points when received
  useEffect(() => {
    const message = lastJsonMessage as Message<SosContent> | null;
    if (message && (message.type === "sos" || message.type === "alert")) {
      const { latitude, longitude } = message.content.location;
      console.log("YE MILA HIA:", message.content.location);
      // Convert to [lon, lat]
      setAlertPoints((pts) => [
        ...pts,
        [latitude, longitude] as [number, number],
      ]);
    }
  }, [lastJsonMessage]);

  // Setup (or refresh) OpenLayers map and blinking alert layer
  useEffect(() => {
    if (!mapRef.current) return;

    // Create Features for alert points
    const features = alertPoints.map(
      (coord) => new Feature(new Point(fromLonLat(coord)))
    );

    // Remove old alert layer if needed
    if (alertLayerRef.current && mapInstance.current) {
      mapInstance.current.removeLayer(alertLayerRef.current);
    }

    // Create vector source and layer for alerts
    const alertSource = new VectorSource({ features });

    // Blinking style parameters
    let radius = 7;
    let growing = true;
    const alertStyle = new Style({
      image: new CircleStyle({
        radius,
        fill: new Fill({ color: "rgba(255, 0, 0, 0.7)" }),
        stroke: new Stroke({ color: "rgba(255, 0, 0, 1)", width: 2 }),
      }),
    });
    const alertLayer = new VectorLayer({
      source: alertSource,
      style: alertStyle,
    });
    alertLayerRef.current = alertLayer;

    // If map already exists, just add (refresh) the alert layer
    if (mapInstance.current) {
      mapInstance.current.addLayer(alertLayer);
    } else {
      // Create map
      const map = new Map({
        target: mapRef.current,
        layers: [
          new TileLayer({
            source: new OSM({
              url: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png",
            }),
          }),
          alertLayer,
        ],
        view: new View({
          center: mapCenter ? fromLonLat(mapCenter) : fromLonLat([0, 0]),
          zoom: 15,
        }),
        controls: [],
      });
      mapInstance.current = map;
    }

    // Animation: update circle radius for blinking
    let animationFrame = 0;
    const animate = () => {
      if (!alertLayerRef.current) return;

      const style = alertLayerRef.current.getStyle() as Style;
      const image = style.getImage() as CircleStyle;

      if (growing) {
        radius += 0.3;
        if (radius > 12) growing = false;
      } else {
        radius -= 0.3;
        if (radius < 7) growing = true;
      }
      image.setRadius(radius);
      alertLayerRef.current.setStyle(style);

      animationFrame = requestAnimationFrame(animate);
    };
    animate();

    return () => {
      if (animationFrame) cancelAnimationFrame(animationFrame);
      if (mapInstance.current && alertLayerRef.current)
        mapInstance.current.removeLayer(alertLayerRef.current);
    };
    // update whenever alertPoints changes
  }, [alertPoints, mapCenter]);

  // Update center when prop changes
  useEffect(() => {
    if (mapCenter && mapInstance.current) {
      mapInstance.current.getView().setCenter(fromLonLat(mapCenter));
      mapInstance.current.getView().setZoom(15);
    }
  }, [mapCenter]);

  return <div ref={mapRef} className="w-full h-full" />;
}
