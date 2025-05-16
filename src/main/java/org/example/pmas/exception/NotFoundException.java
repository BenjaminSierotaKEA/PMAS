package org.example.pmas.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int id) {
        super("Service error: id " + id + " does not exist");
    }
}
