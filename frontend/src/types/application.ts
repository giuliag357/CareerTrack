// Gli stati possibili (come l'enum ApplicationStatus del backend)
export type ApplicationStatus =
    | "TO_APPLY"
    | "APPLIED"
    | "INTERVIEW"
    | "REJECTED"
    | "OFFER"
    | "WITHDRAWN";

// Le modalità di lavoro (come l'enum WorkMode del backend)
export type WorkMode = "ONSITE" | "HYBRID" | "REMOTE";

// Una candidatura, esattamente come la restituisce l'API (JobApplicationResponse)
export interface JobApplication {
    id: number;
    company: string;
    position: string;
    jobUrl: string | null;
    description: string | null;
    location: string | null;
    workMode: WorkMode | null;
    status: ApplicationStatus;
    applicationDate: string | null;
    notes: string | null;
    createdAt: string;
    updatedAt: string;
}

// L'elenco degli stati, nell'ordine in cui vogliamo mostrarli
export const APPLICATION_STATUSES: ApplicationStatus[] = [
    "TO_APPLY",
    "APPLIED",
    "INTERVIEW",
    "OFFER",
    "REJECTED",
    "WITHDRAWN",
];

// Le etichette in italiano da mostrare all'utente
export const STATUS_LABELS: Record<ApplicationStatus, string> = {
    TO_APPLY: "Da candidarsi",
    APPLIED: "Candidata",
    INTERVIEW: "Colloquio",
    OFFER: "Offerta",
    REJECTED: "Rifiutata",
    WITHDRAWN: "Ritirata",
};

export const WORK_MODE_LABELS: Record<WorkMode, string> = {
    ONSITE: "In sede",
    HYBRID: "Ibrido",
    REMOTE: "Da remoto",
};