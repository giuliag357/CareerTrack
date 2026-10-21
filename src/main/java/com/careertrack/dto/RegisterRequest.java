package com.careertrack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "L'email non è valida")
        @Size(max = 255, message = "L'email può avere al massimo 255 caratteri")
        String email,

        @NotBlank(message = "Il nome è obbligatorio")
        @Size(max = 100, message = "Il nome può avere al massimo 100 caratteri")
        String name,

        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 8, max = 72, message = "La password deve avere tra 8 e 72 caratteri")
        String password
) {
}
