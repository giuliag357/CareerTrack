package com.careertrack.dto;

import com.careertrack.entity.ApplicationStatus;
import com.careertrack.entity.JobApplication;
import com.careertrack.entity.WorkMode;

import java.time.Instant;
import java.time.LocalDate;

public record JobApplicationResponse(
        Long id,
        String company,
        String position,
        String jobUrl,
        String description,
        String location,
        WorkMode workMode,
        ApplicationStatus status,
        LocalDate applicationDate,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
    // Converte un'entity in un DTO di risposta
    public static JobApplicationResponse from(JobApplication application) {
        return new JobApplicationResponse(
                application.getId(),
                application.getCompany(),
                application.getPosition(),
                application.getJobUrl(),
                application.getDescription(),
                application.getLocation(),
                application.getWorkMode(),
                application.getStatus(),
                application.getApplicationDate(),
                application.getNotes(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }
}
