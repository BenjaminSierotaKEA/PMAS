package org.example.pmas.exception;

public class WrongInputException extends RuntimeException {
    public WrongInputException(String message) {
        super(message);
    }
}
