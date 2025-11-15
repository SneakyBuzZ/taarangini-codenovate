import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Progress } from "@/components/ui/progress";
import { Separator } from "@/components/ui/separator";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { CheckCircle, Clock, MapPin, Search, Shield, User, XCircle } from "lucide-react";

export default function DigitalIdPage() {
    return (
        <div className="flex-1 space-y-8 p-4 md:p-8 bg-gradient-to-br from-slate-900 via-slate-800 to-slate-700 text-slate-100 min-h-screen">
            <div className="flex items-center justify-between space-y-2">
                <div>
                    <h1 className="text-3xl font-headline font-bold tracking-tight text-slate-100">Digital Tourist ID</h1>
                    <p className="text-slate-400">
                        Securely access and verify tourist information.
                    </p>
                </div>
            </div>

            <Card className="bg-slate-800/90 border-slate-700 backdrop-blur-sm shadow-xl">
                <CardHeader>
                    <CardTitle className="font-headline text-white">Search Tourist</CardTitle>
                    <CardDescription className="text-slate-300">Enter a Tourist ID, Passport Number, or Aadhaar Number to retrieve their digital ID.</CardDescription>
                </CardHeader>
                <CardContent>
                    <div className="flex w-full max-w-md items-center space-x-2">
                        <Input type="text" placeholder="e.g., T-84392" className="h-10 bg-slate-700/50 border-slate-600 text-white placeholder:text-slate-400" />
                        <Button type="submit" className="h-10 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700 text-white shadow-lg">
                            <Search className="mr-2 h-4 w-4" /> Search
                        </Button>
                    </div>
                </CardContent>
            </Card>

            <div className="grid gap-8 md:grid-cols-3">
                <div className="md:col-span-1">
                    <Card className="overflow-hidden bg-slate-800/90 border-slate-700 backdrop-blur-sm shadow-xl">
                        <div className="bg-gradient-to-br from-slate-700/60 to-slate-800/40 p-6 flex flex-col items-center text-center">
                             <div className="relative">
                                <Avatar className="h-24 w-24 border-4 border-blue-500 shadow-lg">
                                    <div className="w-full h-full bg-gradient-to-br from-blue-400 to-blue-600 rounded-full flex items-center justify-center text-white text-2xl font-bold">
                                        JD
                                    </div>
                                    <AvatarFallback>JD</AvatarFallback>
                                </Avatar>
                                <Badge variant="default" className="absolute -bottom-1 -right-2 border-2 border-blue-500 bg-emerald-600 text-white">
                                    <CheckCircle className="h-3 w-3 mr-1" />
                                    Verified
                                </Badge>
                             </div>
                            <h2 className="text-2xl font-bold font-headline mt-4 text-white">Jane Doe</h2>
                            <p className="text-sm text-slate-300">Tourist ID: T-84392</p>
                        </div>
                        <CardContent className="p-6 space-y-4">
                            <div>
                                <h3 className="text-sm font-medium text-slate-200 mb-2">Tourist Safety Score</h3>
                                <div className="flex items-center gap-3">
                                    <Progress value={85} className="h-2" />
                                    <span className="font-bold text-slate-100 text-lg">85</span>
                                </div>
                                <p className="text-xs text-slate-400 mt-1">Based on travel patterns and area sensitivity.</p>
                            </div>
                            <Separator className="bg-slate-600" />
                             <div className="space-y-3 text-sm">
                                <div className="flex items-start">
                                    <User className="h-4 w-4 mr-3 mt-0.5 text-slate-300" />
                                    <span className="text-slate-200">Passport: <span className="font-medium text-white">L345XXX</span></span>
                                </div>
                                 <div className="flex items-start">
                                    <Shield className="h-4 w-4 mr-3 mt-0.5 text-slate-300" />
                                    <span className="text-slate-200">Visit Valid Until: <span className="font-medium text-white">2024-08-15</span></span>
                                </div>
                                <div className="flex items-start">
                                    <MapPin className="h-4 w-4 mr-3 mt-0.5 text-slate-300" />
                                    <span className="text-slate-200">Emergency Contact: <span className="font-medium text-white">+91 9876543210</span></span>
                                </div>
                            </div>
                        </CardContent>
                    </Card>
                </div>
                <div className="md:col-span-2">
                    <Card className="bg-slate-800/90 border-slate-700 shadow-lg">
                        <CardHeader className="bg-gradient-to-r from-slate-700 to-slate-800 rounded-t-lg border-b border-slate-600">
                            <CardTitle className="font-headline text-white">Trip Itinerary & Status</CardTitle>
                        </CardHeader>
                        <CardContent className="p-6">
                            <Table>
                                <TableHeader>
                                    <TableRow className="border-slate-700 hover:bg-slate-800/50">
                                        <TableHead className="text-slate-200 font-medium">Date</TableHead>
                                        <TableHead className="text-slate-200 font-medium">Location</TableHead>
                                        <TableHead className="text-slate-200 font-medium">Status</TableHead>
                                    </TableRow>
                                </TableHeader>
                                <TableBody>
                                    <TableRow className="border-slate-700 hover:bg-slate-800/30">
                                        <TableCell className="text-slate-100">2024-07-20</TableCell>
                                        <TableCell className="text-slate-100">Arrival at Airport</TableCell>
                                        <TableCell>
                                            <Badge variant="default" className="bg-emerald-600 text-white hover:bg-emerald-700"><CheckCircle className="h-3 w-3 mr-1.5" />Completed</Badge>
                                        </TableCell>
                                    </TableRow>
                                    <TableRow className="border-slate-700 hover:bg-slate-800/30">
                                        <TableCell className="text-slate-100">2024-07-21</TableCell>
                                        <TableCell className="text-slate-100">Check-in at Grand Hotel</TableCell>
                                         <TableCell>
                                            <Badge variant="default" className="bg-emerald-600 text-white hover:bg-emerald-700"><CheckCircle className="h-3 w-3 mr-1.5" />Completed</Badge>
                                        </TableCell>
                                    </TableRow>
                                     <TableRow className="bg-slate-800/60 border-slate-700 hover:bg-slate-800/40">
                                        <TableCell className="text-white font-medium">2024-07-22</TableCell>
                                        <TableCell className="text-white font-medium">City Tour (Old Town)</TableCell>
                                         <TableCell>
                                            <Badge variant="secondary" className="bg-amber-600 text-white hover:bg-amber-700"><Clock className="h-3 w-3 mr-1.5" />In Progress</Badge>
                                        </TableCell>
                                    </TableRow>
                                    <TableRow className="border-slate-700 hover:bg-slate-800/30">
                                        <TableCell className="text-slate-100">2024-07-23</TableCell>
                                        <TableCell className="text-slate-100">Mountain Trail Hike</TableCell>
                                         <TableCell>
                                            <Badge variant="outline" className="border-blue-600 text-blue-400 hover:bg-blue-600/10">Upcoming</Badge>
                                        </TableCell>
                                    </TableRow>
                                     <TableRow className="border-slate-700 hover:bg-slate-800/30">
                                        <TableCell className="text-slate-100">2024-07-24</TableCell>
                                        <TableCell className="text-slate-100">Departure from Airport</TableCell>
                                         <TableCell>
                                            <Badge variant="outline" className="border-blue-600 text-blue-400 hover:bg-blue-600/10">Upcoming</Badge>
                                        </TableCell>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </CardContent>
                    </Card>
                     <Card className="mt-8 bg-slate-800/90 border-slate-700 shadow-lg">
                        <CardHeader className="bg-gradient-to-r from-slate-700 to-slate-800 rounded-t-lg border-b border-slate-600">
                            <CardTitle className="font-headline text-white">Recent Activity & Alerts</CardTitle>
                        </CardHeader>
                        <CardContent className="p-6">
                             <div className="space-y-4">
                                <div className="flex items-center gap-4">
                                    <div className="flex-shrink-0 bg-rose-500/20 text-rose-400 p-2 rounded-full border border-rose-500/30">
                                        <XCircle className="h-5 w-5" />
                                    </div>
                                    <div>
                                        <p className="font-medium text-white">Geo-Fence Exit Alert</p>
                                        <p className="text-sm text-slate-300">Exited 'City Center' zone at 2024-07-21 14:30:15</p>
                                    </div>
                                    <Badge variant="destructive" className="ml-auto bg-rose-600 text-white hover:bg-rose-700">Investigating</Badge>
                                </div>
                                 <div className="flex items-center gap-4">
                                    <div className="flex-shrink-0 bg-emerald-500/20 text-emerald-400 p-2 rounded-full border border-emerald-500/30">
                                        <CheckCircle className="h-5 w-5" />
                                    </div>
                                    <div>
                                        <p className="font-medium text-white">Geo-Fence Entry</p>
                                        <p className="text-sm text-slate-300">Entered 'High-Risk Zone A' at 2024-07-21 13:10:00</p>
                                    </div>
                                    <Badge variant="default" className="ml-auto bg-emerald-600 text-white hover:bg-emerald-700">Resolved</Badge>
                                </div>
                            </div>
                        </CardContent>
                    </Card>
                </div>
            </div>
        </div>
    );
}