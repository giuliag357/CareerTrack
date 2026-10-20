package com.careertrack.exception;

public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException(Long id) {
        super("Candidatura con id " + id + " non trovata");
    }
}
