package org.example.pmas.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(int id) {
        super("id: " + id + " doesnt exist");
    }

    public NotFoundException(String message) {
    }
}
