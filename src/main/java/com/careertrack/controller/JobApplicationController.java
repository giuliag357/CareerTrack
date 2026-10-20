package com.careertrack.controller;

import com.careertrack.dto.JobApplicationRequest;
import com.careertrack.dto.JobApplicationResponse;
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
    // GET /api/applications -> elenco di tutte le candidature
    @GetMapping
    public List<JobApplicationResponse> getAll() {
        return service.findAll();
    }

    // GET /api/applications/{id} -> una candidatura (oppure 404)
    @GetMapping("/{id}")
    public JobApplicationResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/applications -> crea una candidatura (201 + header Location)
    @PostMapping
    public ResponseEntity<JobApplicationResponse> create(
            @Valid @RequestBody JobApplicationRequest request) {

        JobApplicationResponse created = service.create(request);
        URI location = URI.create("/api/applications/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    // PUT /api/applications/{id} -> modifica una candidatura
    @PutMapping("/{id}")
    public JobApplicationResponse update(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request) {

        return service.update(id, request);
    }

    // DELETE /api/applications/{id} -> elimina una candidatura (204)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
