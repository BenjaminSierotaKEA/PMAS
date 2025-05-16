package org.example.pmas.exception;

public class UpdateObjectException extends RuntimeException {
    public UpdateObjectException(int id) {
        super("service error: Couldn't update object with id: " + id);
    }
}
