import { useState } from "react";
import ApplicationList from "./components/ApplicationList";
import { mockApplications } from "./data/mockApplications";
import {
  APPLICATION_STATUSES,
  STATUS_LABELS,
  type ApplicationStatus,
} from "./types/application";

// Il filtro può essere uno stato oppure "ALL" (tutti)
type StatusFilter = ApplicationStatus | "ALL";

function App() {
  // Lo STATO: quale filtro ha scelto l'utente
  const [statusFilter, setStatusFilter] = useState<StatusFilter>("ALL");

  // Un valore CALCOLATO dallo stato
  const visibleApplications =
      statusFilter === "ALL"
          ? mockApplications
          : mockApplications.filter((application) => application.status === statusFilter);

  return (
      <main className="container">
        <header>
          <h1>CareerTrack</h1>
          <p>{visibleApplications.length} candidature</p>
        </header>

        <label className="filter">
          Filtra per stato:{" "}
          <select
              value={statusFilter}
              onChange={(event) => setStatusFilter(event.target.value as StatusFilter)}
          >
            <option value="ALL">Tutti</option>
            {APPLICATION_STATUSES.map((status) => (
                <option key={status} value={status}>
                  {STATUS_LABELS[status]}
                </option>
            ))}
          </select>
        </label>

        <ApplicationList applications={visibleApplications} />
      </main>
  );
}

export default App;