import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Textarea } from "@/components/ui/textarea";
import { MoreHorizontal, PlusCircle } from "lucide-react";

const incidents = [
    { id: 'INC-001', type: 'Missing Person', location: 'City Center', reportedBy: 'PO-1234', status: 'Urgent' },
    { id: 'INC-002', type: 'Theft', location: 'Old Town Market', reportedBy: 'PO-5678', status: 'Active' },
    { id: 'INC-003', type: 'Medical Emergency', location: 'Beachfront', reportedBy: 'PO-1234', status: 'Resolved' },
    { id: 'INC-004', type: 'Suspicious Activity', location: 'Main Station', reportedBy: 'PO-9012', status: 'Active' },
    { id: 'INC-005', type: 'Disturbance', location: 'Grand Hotel', reportedBy: 'PO-5678', status: 'Resolved' },
];

export default function IncidentsPage() {
  return (
    <div className="flex-1 space-y-8 p-4 md:p-8 bg-gradient-to-br from-slate-900 via-slate-800 to-slate-700 text-slate-100 min-h-screen">
      <div className="flex items-center justify-between space-y-2">
        <div>
          <h1 className="text-3xl font-headline font-bold tracking-tight text-slate-100">Incident Management</h1>
          <p className="text-slate-400">Report new incidents and track existing ones.</p>
        </div>
        <Button className="bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white shadow-md transition-all duration-200">
          <PlusCircle className="mr-2 h-4 w-4" />
          Report New Incident
        </Button>
      </div>

      <div className="grid gap-8 md:grid-cols-3">
        <div className="md:col-span-2">
          <Card className="bg-slate-800/90 border-slate-700 shadow-lg">
            <CardHeader className="bg-gradient-to-r from-slate-700 to-slate-800 rounded-t-lg border-b border-slate-600">
              <CardTitle className="font-headline text-white">All Incidents</CardTitle>
              <CardDescription className="text-slate-300">A log of all reported incidents.</CardDescription>
            </CardHeader>
            <CardContent className="p-6">
                <Table>
                    <TableHeader>
                        <TableRow className="border-slate-700 hover:bg-slate-800/50">
                            <TableHead className="text-slate-200 font-medium">ID</TableHead>
                            <TableHead className="text-slate-200 font-medium">Type</TableHead>
                            <TableHead className="text-slate-200 font-medium">Location</TableHead>
                            <TableHead className="text-slate-200 font-medium">Status</TableHead>
                            <TableHead className="text-slate-200 font-medium"><span className="sr-only">Actions</span></TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {incidents.map((incident) => (
                            <TableRow key={incident.id} className="border-slate-700 hover:bg-slate-800/30">
                                <TableCell className="font-medium text-white">{incident.id}</TableCell>
                                <TableCell className="text-slate-100">{incident.type}</TableCell>
                                <TableCell className="text-slate-100">{incident.location}</TableCell>
                                <TableCell>
                                    <Badge variant={incident.status === 'Urgent' ? 'destructive' : incident.status === 'Active' ? 'secondary' : 'default'}
                                           className={
                                               incident.status === 'Urgent' ? 'bg-rose-600 text-white hover:bg-rose-700' :
                                               incident.status === 'Active' ? 'bg-amber-600 text-white hover:bg-amber-700' :
                                               'bg-emerald-600 text-white hover:bg-emerald-700'
                                           }>
                                        {incident.status}
                                    </Badge>
                                </TableCell>
                                <TableCell>
                                    <DropdownMenu>
                                        <DropdownMenuTrigger asChild>
                                            <Button variant="ghost" className="h-8 w-8 p-0 text-slate-300 hover:text-white hover:bg-slate-700">
                                                <span className="sr-only">Open menu</span>
                                                <MoreHorizontal className="h-4 w-4" />
                                            </Button>
                                        </DropdownMenuTrigger>
                                        <DropdownMenuContent align="end" className="bg-slate-800 border-slate-600 shadow-lg">
                                            <DropdownMenuItem className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">View Details</DropdownMenuItem>
                                            <DropdownMenuItem className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Assign Officer</DropdownMenuItem>
                                            <DropdownMenuItem className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Update Status</DropdownMenuItem>
                                        </DropdownMenuContent>
                                    </DropdownMenu>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </CardContent>
          </Card>
        </div>
        <div className="md:col-span-1">
          <Card className="bg-slate-800/90 border-slate-700 shadow-lg">
            <CardHeader className="bg-gradient-to-r from-slate-700 to-slate-800 rounded-t-lg border-b border-slate-600">
              <CardTitle className="font-headline text-white">Report an Incident</CardTitle>
              <CardDescription className="text-slate-300">Fill out the form below to report a new incident.</CardDescription>
            </CardHeader>
            <CardContent className="p-6 space-y-4">
              <div className="space-y-2">
                <Label htmlFor="type" className="text-slate-200 font-medium">Incident Type</Label>
                <Select>
                  <SelectTrigger className="bg-slate-700/50 border-slate-600 text-white focus:border-blue-500 focus:ring-blue-500/20">
                    <SelectValue placeholder="Select type" />
                  </SelectTrigger>
                  <SelectContent className="bg-slate-800 border-slate-600 shadow-lg">
                    <SelectItem value="missing" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Missing Person</SelectItem>
                    <SelectItem value="theft" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Theft</SelectItem>
                    <SelectItem value="medical" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Medical Emergency</SelectItem>
                    <SelectItem value="suspicious" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Suspicious Activity</SelectItem>
                    <SelectItem value="other" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Other</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <div className="space-y-2">
                <Label htmlFor="location" className="text-slate-200 font-medium">Location</Label>
                <Input id="location" placeholder="e.g., City Center" className="bg-slate-700/50 border-slate-600 text-white placeholder:text-slate-400 focus:border-blue-500 focus:ring-blue-500/20" />
              </div>
              <div className="space-y-2">
                <Label htmlFor="severity" className="text-slate-200 font-medium">Severity</Label>
                <Select>
                  <SelectTrigger className="bg-slate-700/50 border-slate-600 text-white focus:border-blue-500 focus:ring-blue-500/20">
                    <SelectValue placeholder="Select severity" />
                  </SelectTrigger>
                  <SelectContent className="bg-slate-800 border-slate-600 shadow-lg">
                    <SelectItem value="low" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Low</SelectItem>
                    <SelectItem value="medium" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Medium</SelectItem>
                    <SelectItem value="high" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">High</SelectItem>
                    <SelectItem value="urgent" className="text-slate-100 hover:bg-slate-700 focus:bg-slate-700">Urgent</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <div className="space-y-2">
                <Label htmlFor="description" className="text-slate-200 font-medium">Description</Label>
                <Textarea id="description" placeholder="Provide details about the incident..." className="bg-slate-700/50 border-slate-600 text-white placeholder:text-slate-400 focus:border-blue-500 focus:ring-blue-500/20" />
              </div>
              <Button className="w-full bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white shadow-md transition-all duration-200">Submit Report</Button>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
