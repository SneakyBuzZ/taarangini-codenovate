'use client';

import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { generateEFIR, GenerateEFIROutput } from '@/ai/flows/automate-e-fir-generation';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Textarea } from '@/components/ui/textarea';
import { Loader2, FileText } from 'lucide-react';
import { useToast } from '@/hooks/use-toast';

const formSchema = z.object({
  reportText: z.string().min(50, {
    message: 'Report text must be at least 50 characters.',
  }),
});

export default function EFIRForm() {
  const [firDetails, setFirDetails] = useState<GenerateEFIROutput['firDetails'] | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const { toast } = useToast();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      reportText: '',
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    setIsLoading(true);
    setFirDetails(null);
    try {
      const result = await generateEFIR(values);
      setFirDetails(result.firDetails);
      toast({
        title: "E-FIR Generated",
        description: "The E-FIR details have been successfully extracted.",
      });
    } catch (error) {
      console.error('E-FIR generation failed:', error);
      toast({
        variant: "destructive",
        title: "Generation Failed",
        description: "Could not generate E-FIR from the provided text.",
      });
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className="grid gap-8 md:grid-cols-2">
      <Card className="bg-slate-800/90 border-slate-700 shadow-lg">
        <CardHeader className="bg-gradient-to-r from-slate-700 to-slate-800 rounded-t-lg border-b border-slate-600">
          <CardTitle className="font-headline text-white">Incident Report</CardTitle>
          <CardDescription className="text-slate-300">
            Paste the initial report text from any source. The AI will extract the relevant information.
          </CardDescription>
        </CardHeader>
        <CardContent className="p-6">
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="reportText"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="text-slate-200 font-medium">Report Text</FormLabel>
                    <FormControl>
                      <Textarea
                        placeholder="e.g., 'My son, John Doe, 24 years old, went missing yesterday from our home at 123 Main St. He was last seen wearing a blue jacket. He has a scar on his left cheek. My contact is 555-1234...'"
                        className="min-h-[300px] resize-y bg-slate-700/50 border-slate-600 text-white placeholder:text-slate-400 focus:border-blue-500 focus:ring-blue-500/20"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button 
                type="submit" 
                disabled={isLoading} 
                className="bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white shadow-md transition-all duration-200"
              >
                {isLoading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                Generate E-FIR
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
      
      <Card className="bg-slate-800/90 border-slate-700 shadow-lg">
        <CardHeader className="bg-gradient-to-r from-slate-700 to-slate-800 rounded-t-lg border-b border-slate-600">
          <CardTitle className="font-headline text-white">Generated E-FIR Details</CardTitle>
          <CardDescription className="text-slate-300">
            Review the automatically generated First Information Report.
          </CardDescription>
        </CardHeader>
        <CardContent className="p-6 space-y-4">{/* Content continues below */}
          {isLoading && (
            <div className="flex h-[380px] items-center justify-center p-8">
              <Loader2 className="h-8 w-8 animate-spin text-blue-400" />
            </div>
          )}
          {firDetails && (
            <div className="space-y-3 text-sm">
                <div className="grid grid-cols-3 gap-2 items-center">
                    <span className="font-semibold text-slate-200">Missing Person:</span>
                    <span className="col-span-2 text-white">{firDetails.missingPersonName}</span>
                </div>
                <div className="grid grid-cols-3 gap-2 items-center">
                    <span className="font-semibold text-slate-200">Age:</span>
                    <span className="col-span-2 text-white">{firDetails.age}</span>
                </div>
                 <div className="grid grid-cols-3 gap-2 items-center">
                    <span className="font-semibold text-slate-200">Gender:</span>
                    <span className="col-span-2 text-white">{firDetails.gender}</span>
                </div>
                <div className="grid grid-cols-3 gap-2 items-center">
                    <span className="font-semibold text-slate-200">Last Seen Address:</span>
                    <span className="col-span-2 text-white">{firDetails.addressLastSeen}</span>
                </div>
                <div className="grid grid-cols-3 gap-2 items-center">
                    <span className="font-semibold text-slate-200">Identifying Marks:</span>
                    <span className="col-span-2 text-white">{firDetails.identifyingMarks}</span>
                </div>
                <div className="grid grid-cols-3 gap-2 items-center">
                    <span className="font-semibold text-slate-200">Contact Info:</span>
                    <span className="col-span-2 text-white">{firDetails.contactInformation}</span>
                </div>
                <div className="grid grid-cols-3 gap-2">
                    <span className="font-semibold text-slate-200">Additional Details:</span>
                    <p className="col-span-2 leading-relaxed text-white">{firDetails.additionalDetails}</p>
                </div>
                 <Button className="w-full mt-4 bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white shadow-md transition-all duration-200">File E-FIR</Button>
            </div>
          )}
          {!isLoading && !firDetails && (
            <div className="flex flex-col h-[380px] items-center justify-center text-center p-8 rounded-lg border border-dashed border-slate-600">
              <FileText className="h-10 w-10 text-slate-400 mb-4" />
              <p className="text-slate-300">E-FIR details will appear here once generated.</p>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
