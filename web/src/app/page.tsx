import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardDescription,
} from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import ActiveStats from "@/components/dashboard/active-stats";
import HeatMap from "@/components/dashboard/heat-map";

export default function Home() {
  return (
    <div className="flex-1 space-y-8 p-6 md:p-8 min-h-screen">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div className="space-y-1">
          <h1 className="text-2xl font-headline font-bold gradient-text">
            Dashboard
          </h1>
          <p className="text-neutral-400 font-medium">
            Real-time overview of tourist safety and incidents
          </p>
        </div>
      </div>

      <div className="flex flex-col justify-center items-start w-full space-y-4">
        <ActiveStats />
        <HeatMap />
      </div>
      {/* 
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        <Card className="col-span-4 lg:col-span-3 bg-slate-800/50 border-slate-700 backdrop-blur-sm">
          <CardHeader>
            <CardTitle className="font-headline text-white">
              Recent Incidents
            </CardTitle>
            <CardDescription className="text-slate-300">
              A list of the most recent incidents reported.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow className="border-slate-700">
                  <TableHead className="text-slate-300">Incident</TableHead>
                  <TableHead className="text-slate-300">Location</TableHead>
                  <TableHead className="text-slate-300">Status</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                <TableRow className="border-slate-800 hover:bg-slate-800/30">
                  <TableCell>
                    <div className="font-medium text-white">Missing Person</div>
                    <div className="text-sm text-slate-400">John Doe, 24</div>
                  </TableCell>
                  <TableCell className="text-slate-200">City Center</TableCell>
                  <TableCell>
                    <Badge
                      variant="destructive"
                      className="bg-rose-600 text-white"
                    >
                      Urgent
                    </Badge>
                  </TableCell>
                </TableRow>
                <TableRow className="border-slate-800 hover:bg-slate-800/30">
                  <TableCell>
                    <div className="font-medium text-white">Minor Theft</div>
                    <div className="text-sm text-slate-400">
                      Tourist reported stolen wallet
                    </div>
                  </TableCell>
                  <TableCell className="text-slate-200">
                    Old Town Market
                  </TableCell>
                  <TableCell>
                    <Badge
                      variant="secondary"
                      className="bg-amber-600 text-white"
                    >
                      Active
                    </Badge>
                  </TableCell>
                </TableRow>
                <TableRow className="border-slate-800 hover:bg-slate-800/30">
                  <TableCell>
                    <div className="font-medium text-white">
                      Medical Emergency
                    </div>
                    <div className="text-sm text-slate-400">
                      Tourist fainted
                    </div>
                  </TableCell>
                  <TableCell className="text-slate-200">Beachfront</TableCell>
                  <TableCell>
                    <Badge className="bg-emerald-600 text-white">
                      Resolved
                    </Badge>
                  </TableCell>
                </TableRow>
                <TableRow className="border-slate-800 hover:bg-slate-800/30">
                  <TableCell>
                    <div className="font-medium text-white">
                      Suspicious Activity
                    </div>
                    <div className="text-sm text-slate-400">
                      Unattended baggage
                    </div>
                  </TableCell>
                  <TableCell className="text-slate-200">Main Station</TableCell>
                  <TableCell>
                    <Badge
                      variant="secondary"
                      className="bg-amber-600 text-white"
                    >
                      Active
                    </Badge>
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div> */}
    </div>
  );
}
