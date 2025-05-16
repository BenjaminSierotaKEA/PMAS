package org.example.pmas.exception;

public class UpdateObjectException extends RuntimeException {
    public UpdateObjectException(int id) {
        super("service error: Something went wrong after updating object with id: " + id);
    }
}
