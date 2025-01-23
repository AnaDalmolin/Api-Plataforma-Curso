package com.example.desafio01.desafio01.exceptions;

public class ProfessorServerException extends RuntimeException {
    
    public ProfessorServerException(String message) {
        super(message);
    }
    
    public ProfessorServerException(String message, String details) {

        super(message + ": " + details);

    }

}
