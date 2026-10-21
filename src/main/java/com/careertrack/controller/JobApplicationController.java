package com.careertrack.controller;

import com.careertrack.dto.JobApplicationRequest;
import com.careertrack.dto.JobApplicationResponse;
import com.careertrack.security.CurrentUserId;
import com.careertrack.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public List<JobApplicationResponse> getAll(@CurrentUserId Long userId) {
        return service.findAll(userId);
    }

    @GetMapping("/{id}")
    public JobApplicationResponse getById(@CurrentUserId Long userId,
                                          @PathVariable Long id) {
        return service.findById(userId, id);
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponse> create(
            @CurrentUserId Long userId,
            @Valid @RequestBody JobApplicationRequest request) {

        JobApplicationResponse created = service.create(userId, request);
        URI location = URI.create("/api/applications/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public JobApplicationResponse update(
            @CurrentUserId Long userId,
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request) {

        return service.update(userId, id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@CurrentUserId Long userId,
                       @PathVariable Long id) {
        service.delete(userId, id);
    }
}