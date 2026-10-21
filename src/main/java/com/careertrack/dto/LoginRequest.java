package com.careertrack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "L'email non è valida")
        String email,

        @NotBlank(message = "La password è obbligatoria")
        String password
) {
}
