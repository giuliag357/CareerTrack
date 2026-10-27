import ApplicationCard from "./ApplicationCard";
import type { JobApplication } from "../types/application";

interface ApplicationListProps {
    applications: JobApplication[];
}

function ApplicationList({ applications }: ApplicationListProps) {
    // Caso particolare: niente da mostrare
    if (applications.length === 0) {
        return <p className="empty">Nessuna candidatura da mostrare.</p>;
    }

    return (
        <div className="list">
            {applications.map((application) => (
                <ApplicationCard key={application.id} application={application} />
            ))}
        </div>
    );
}

export default ApplicationList;