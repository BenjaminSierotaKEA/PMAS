package org.example.pmas.exception;

public class CreateObjectException extends RuntimeException{

    public CreateObjectException(int id) {
        super("Service error: Couldn't create object with id: " + id);
    }
}
