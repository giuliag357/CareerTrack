package com.careertrack.controller;

import com.careertrack.entity.JobApplication;
import com.careertrack.repository.JobApplicationRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationRepository repository;

    public JobApplicationController(
            JobApplicationRepository repository) {
        this.repository = repository;
    }

    // Recupera tutte le candidature
    @GetMapping
    public List<JobApplication> getAllApplications() {
        return repository.findAll();
    }

    // Recupera una candidatura tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getApplicationById(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea una nuova candidatura
    @PostMapping
    public JobApplication createApplication(
            @RequestBody JobApplication application) {

        return repository.save(application);
    }

}
