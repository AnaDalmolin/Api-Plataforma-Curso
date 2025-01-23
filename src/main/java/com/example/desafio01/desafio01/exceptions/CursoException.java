package com.example.desafio01.desafio01.exceptions;

public class CursoException extends RuntimeException {

    public CursoException(String message) {
        super(message);
    }
    
    public CursoException(String message, String details) {

        super(message + ": " + details);

    }
}
