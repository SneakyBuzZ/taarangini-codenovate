import EFIRForm from "@/components/e-fir-form";

export default function EFIRPage() {
  return (
    <div className="flex-1 space-y-8 p-4 md:p-8 bg-gradient-to-br from-slate-900 via-slate-800 to-slate-700 text-slate-100 min-h-screen">
      <div className="flex items-center justify-between space-y-2">
        <div>
          <h1 className="text-3xl font-headline font-bold tracking-tight text-slate-100">Automated E-FIR Generation</h1>
          <p className="text-slate-400">
            Generate E-FIRs for missing person cases using AI.
          </p>
        </div>
      </div>
      <EFIRForm />
    </div>
  );
}
