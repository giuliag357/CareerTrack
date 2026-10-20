package com.careertrack.service;

import com.careertrack.dto.JobApplicationRequest;
import com.careertrack.dto.JobApplicationResponse;
import com.careertrack.entity.JobApplication;
import com.careertrack.exception.ApplicationNotFoundException;
import com.careertrack.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobApplicationService {
    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    // Restituisce tutte le candidature
    @Transactional(readOnly = true)
    public List<JobApplicationResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(JobApplicationResponse::from)
                .toList();
    }

    // Restituisce una candidatura tramite id, oppure lancia 404
    @Transactional(readOnly = true)
    public JobApplicationResponse findById(Long id) {
        return JobApplicationResponse.from(getOrThrow(id));
    }

    // Crea una nuova candidatura
    public JobApplicationResponse create(JobApplicationRequest request) {
        JobApplication application = new JobApplication();
        applyRequest(application, request);
        JobApplication saved = repository.save(application);
        return JobApplicationResponse.from(saved);
    }

    // Modifica una candidatura esistente
    public JobApplicationResponse update(Long id, JobApplicationRequest request) {
        JobApplication application = getOrThrow(id);
        applyRequest(application, request);
        JobApplication saved = repository.saveAndFlush(application);
        return JobApplicationResponse.from(saved);
    }

    // Elimina una candidatura
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ApplicationNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // --- Metodi di supporto (privati) ---

    private JobApplication getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    // Copia i dati dalla richiesta all'entity
    private void applyRequest(JobApplication application, JobApplicationRequest request) {
        application.setCompany(request.company());
        application.setPosition(request.position());
        application.setJobUrl(request.jobUrl());
        application.setDescription(request.description());
        application.setLocation(request.location());
        application.setWorkMode(request.workMode());
        application.setApplicationDate(request.applicationDate());
        application.setNotes(request.notes());

        // Se lo stato non viene indicato, resta quello attuale
        // (per una nuova candidatura: TO_APPLY)
        if (request.status() != null) {
            application.setStatus(request.status());
        }
    }
}
