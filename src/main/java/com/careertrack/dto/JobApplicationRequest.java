package com.careertrack.dto;

import com.careertrack.entity.ApplicationStatus;
import com.careertrack.entity.WorkMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record JobApplicationRequest(
        @NotBlank(message = "L'azienda è obbligatoria")
        @Size(max = 255, message = "Il nome dell'azienda può avere al massimo 255 caratteri")
        String company,

        @NotBlank(message = "La posizione è obbligatoria")
        @Size(max = 255, message = "La posizione può avere al massimo 255 caratteri")
        String position,

        @Size(max = 2048, message = "Il link può avere al massimo 2048 caratteri")
        String jobUrl,

        String description,

        @Size(max = 255, message = "La località può avere al massimo 255 caratteri")
        String location,

        WorkMode workMode,

        ApplicationStatus status,

        @PastOrPresent(message = "La data di candidatura non può essere nel futuro")
        LocalDate applicationDate,

        String notes
) {
}
