"use client";

import React, { useEffect, useState } from "react";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
} from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Message, SosContent } from "@/lib/types/wss-message";
import { LocateIcon } from "lucide-react";
import useWebSocket from "react-use-websocket";
import { SOCKET_URL, TOKEN } from "@/utils/constants";
import clsx from "clsx";
import { motion, AnimatePresence } from "framer-motion";

// Dummy alerts (client-only, initialized in useEffect)
const dummyAlerts: Message<SosContent>[] = [
  {
    type: "sos",
    content: {
      touristId: "0x123456789abcdefadknasdkl893y82na",
      name: "John Doe",
      age: 30,
      gender: "Male",
      location: {
        latitude: 40.7128,
        longitude: -74.006,
      },
      emergencyContact: {
        name: "Jane Doe",
        phone: "+1234567890",
      },
      safetyScore: 45,
    },
    timestamp: Date.now(),
  },
  {
    type: "alert",
    content: {
      touristId: "0xabcdef1234567890asdkjfhgweqoiu12",
      name: "Alice Smith",
      age: 28,
      gender: "Female",
      location: {
        latitude: 34.0522,
        longitude: -118.2437,
      },
      emergencyContact: {
        name: "Bob Smith",
        phone: "+0987654321",
      },
      safetyScore: 60,
    },
    timestamp: Date.now() - 1000000,
  },
  {
    type: "sos",
    content: {
      touristId: "0xdeadbeef1234567890abcdef123456",
      name: "Charlie Brown",
      age: 35,
      gender: "Male",
      location: {
        latitude: 51.5074,
        longitude: -0.1278,
      },
      emergencyContact: {
        name: "Lucy Brown",
        phone: "+1122334455",
      },
      safetyScore: 30,
    },
    timestamp: Date.now() - 2000000,
  },
];

export default function SosAlertsList() {
  const wsUrl = `${SOCKET_URL}?token=${TOKEN}`;
  const { lastJsonMessage } = useWebSocket(wsUrl);

  const [alerts, setAlerts] = useState<Message<SosContent>[]>([]);

  // Initialize dummy alerts client-side only
  useEffect(() => {
    setAlerts(dummyAlerts);
  }, []);

  // Update alerts when a new WebSocket message arrives
  useEffect(() => {
    console.log("Received WebSocket message:", lastJsonMessage);
    const message = lastJsonMessage as Message<SosContent> | null;
    if (message && message.content) {
      setAlerts((prevAlerts) => [message, ...prevAlerts].slice(0, 10));
    }
  }, [lastJsonMessage]);

  return (
    <Card className="w-[28rem] col-span-4 lg:col-span-3 bg-neutral-950/50 border-neutral-800 rounded-none">
      <CardHeader>
        <CardTitle className="text-lg text-white">Emergency Alerts</CardTitle>
        <CardDescription className="text-neutral-400 text-sm">
          A list of the most recent emergency alerts reported.
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="flex flex-col w-full">
          {alerts.length > 0 ? (
            alerts.map((alert, index) => (
              <motion.div
                key={alert.content.touristId + alert.timestamp + index}
                initial={{ opacity: 0, y: -20 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -20 }}
                transition={{ duration: 0.3 }}
              >
                <AlertListItem message={alert} />
              </motion.div>
            ))
          ) : (
            <p className="text-neutral-500 text-sm">
              No incidents reported yet.
            </p>
          )}
        </div>
      </CardContent>
    </Card>
  );
}

function AlertListItem({ message }: { message: Message<SosContent> }) {
  if (!message?.content) return null;
  const safetyColor =
    message.content.safetyScore > 70
      ? "text-green-400"
      : message.content.safetyScore > 40
      ? "text-yellow-400"
      : "text-red-400";

  return (
    <div
      className={clsx(
        "mt-4 p-2 rounded-lg flex justify-center items-center gap-3 text-neutral-400 bg-neutral-900 border border-neutral-800"
      )}
    >
      <div className="h-14 w-14 bg-neutral-800 rounded-md flex justify-center items-center">
        <LocateIcon />
      </div>
      <div className="flex flex-col justify-between items-start flex-1 h-full gap-1">
        <div className="flex w-full items-center">
          <div className="flex flex-col justify-start items-start w-full">
            <span className="text-md text-neutral-200">
              {message.content.name}
            </span>
            <span className="text-xs text-neutral-400">
              {message.content.touristId.slice(0, 8)}...
              {message.content.touristId.slice(-8)}
            </span>
          </div>
          <Badge
            className={clsx(
              "w-20 h-6 flex justify-center items-center text-neutral-200",
              message.type === "sos"
                ? "bg-red-500 hover:bg-red-500/60"
                : "bg-orange-500 hover:bg-orange-500/60"
            )}
          >
            {message.type.toUpperCase()}
          </Badge>
        </div>

        <div className="flex justify-between w-full">
          <span className="text-xs font-semibold">
            Safety:{" "}
            <span className={`font-bold ${safetyColor}`}>
              {message.content.safetyScore}
            </span>
          </span>
          <span className="text-xs text-neutral-500">
            {new Date(message.timestamp).toLocaleString()}
          </span>
        </div>
      </div>
    </div>
  );
}
