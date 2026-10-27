import {
    STATUS_LABELS,
    WORK_MODE_LABELS,
    type JobApplication,
} from "../types/application";

interface ApplicationCardProps {
    application: JobApplication;
}

function ApplicationCard({ application }: ApplicationCardProps) {
    return (
        <article className="card">
            <div className="card-header">
                <h2>{application.position}</h2>
                <span className={`badge badge-${application.status.toLowerCase()}`}>
          {STATUS_LABELS[application.status]}
        </span>
            </div>

            <p className="company">{application.company}</p>

            <p className="details">
                {application.location ?? "Località non indicata"}
                {application.workMode && ` · ${WORK_MODE_LABELS[application.workMode]}`}
            </p>

            {application.applicationDate && (
                <p className="date">
                    Candidatura del{" "}
                    {new Date(application.applicationDate).toLocaleDateString("it-IT")}
                </p>
            )}

            {application.notes && (
                <p className="notes">{application.notes}</p>
            )}
            
        </article>
    );
}

export default ApplicationCard;