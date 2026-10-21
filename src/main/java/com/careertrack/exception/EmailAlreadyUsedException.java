package com.careertrack.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("L'email" + email + "è già registrata");
    }
}
