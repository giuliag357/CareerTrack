package com.careertrack.service;

import com.careertrack.dto.JobApplicationRequest;
import com.careertrack.dto.JobApplicationResponse;
import com.careertrack.entity.JobApplication;
import com.careertrack.exception.ApplicationNotFoundException;
import com.careertrack.repository.JobApplicationRepository;
import com.careertrack.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository repository;
    private final UserRepository userRepository;

    public JobApplicationService(JobApplicationRepository repository,
                                 UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // Tutte le candidature dell'utente, dalla più recente
    @Transactional(readOnly = true)
    public List<JobApplicationResponse> findAll(Long userId) {
        return repository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(JobApplicationResponse::from)
                .toList();
    }

    // Una candidatura, solo se appartiene all'utente
    @Transactional(readOnly = true)
    public JobApplicationResponse findById(Long userId, Long id) {
        return JobApplicationResponse.from(getOrThrow(userId, id));
    }

    // Crea una candidatura che appartiene all'utente
    public JobApplicationResponse create(Long userId, JobApplicationRequest request) {
        JobApplication application = new JobApplication();
        applyRequest(application, request);
        application.setUser(userRepository.getReferenceById(userId));
        JobApplication saved = repository.save(application);
        return JobApplicationResponse.from(saved);
    }

    // Modifica una candidatura, solo se appartiene all'utente
    public JobApplicationResponse update(Long userId, Long id, JobApplicationRequest request) {
        JobApplication application = getOrThrow(userId, id);
        applyRequest(application, request);
        JobApplication saved = repository.saveAndFlush(application);
        return JobApplicationResponse.from(saved);
    }

    // Elimina una candidatura, solo se appartiene all'utente
    public void delete(Long userId, Long id) {
        repository.delete(getOrThrow(userId, id));
    }

    // --- Metodi di supporto (privati) ---

    // Se la candidatura non esiste O appartiene a un altro utente: 404
    private JobApplication getOrThrow(Long userId, Long id) {
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    private void applyRequest(JobApplication application, JobApplicationRequest request) {
        application.setCompany(request.company());
        application.setPosition(request.position());
        application.setJobUrl(request.jobUrl());
        application.setDescription(request.description());
        application.setLocation(request.location());
        application.setWorkMode(request.workMode());
        application.setApplicationDate(request.applicationDate());
        application.setNotes(request.notes());

        if (request.status() != null) {
            application.setStatus(request.status());
        }
    }
}