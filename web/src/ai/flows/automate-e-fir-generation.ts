'use server';

/**
 * @fileOverview E-FIR generation flow for missing person cases.
 *
 * - generateEFIR - A function that handles the E-FIR generation process.
 * - GenerateEFIRInput - The input type for the generateEFIR function.
 * - GenerateEFIROutput - The return type for the generateEFIR function.
 */

import {ai} from '@/ai/genkit';
import {z} from 'genkit';

const GenerateEFIRInputSchema = z.object({
  reportText: z
    .string()
    .describe('The initial report text for the missing person case.'),
});
export type GenerateEFIRInput = z.infer<typeof GenerateEFIRInputSchema>;

const GenerateEFIROutputSchema = z.object({
  firDetails: z.object({
    missingPersonName: z.string().describe('The name of the missing person.'),
    age: z.number().describe('The age of the missing person.'),
    gender: z.string().describe('The gender of the missing person.'),
    addressLastSeen: z
      .string()
      .describe('The last known address of the missing person.'),
    identifyingMarks: z
      .string()
      .describe('Any identifying marks on the missing person.'),
    contactInformation: z
      .string()
      .describe('Contact information for the reporting person.'),
    additionalDetails: z
      .string()
      .describe('Any additional details about the missing person case.'),
  }),
});
export type GenerateEFIROutput = z.infer<typeof GenerateEFIROutputSchema>;

export async function generateEFIR(input: GenerateEFIRInput): Promise<GenerateEFIROutput> {
  return generateEFIRFlow(input);
}

const prompt = ai.definePrompt({
  name: 'generateEFIRPrompt',
  input: {schema: GenerateEFIRInputSchema},
  output: {schema: GenerateEFIROutputSchema},
  prompt: `You are an AI assistant that generates E-FIRs (First Information Reports) for missing person cases.
  Extract key information from the following initial report text to populate the E-FIR details.

  Report Text: {{{reportText}}}

  Provide the output in JSON format with the following schema:
  {
    "firDetails": {
      "missingPersonName": "",
      "age": 0,
      "gender": "",
      "addressLastSeen": "",
      "identifyingMarks": "",
      "contactInformation": "",
      "additionalDetails": ""
    }
  }
  `,
});

const generateEFIRFlow = ai.defineFlow(
  {
    name: 'generateEFIRFlow',
    inputSchema: GenerateEFIRInputSchema,
    outputSchema: GenerateEFIROutputSchema,
  },
  async input => {
    const {output} = await prompt(input);
    return output!;
  }
);
