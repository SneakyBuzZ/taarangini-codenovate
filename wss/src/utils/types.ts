export type Message<T> = {
  type: "sos" | "alert" | "info";
  content: T;
  timestamp: number;
};

export type SosContent = {
  touristId: string;
  name: string;
  age: number;
  gender: string;
  location: {
    latitude: number;
    longitude: number;
  };
  emergencyContact: {
    name: string;
    phone: string;
  };
  safetyScore: number;
};
