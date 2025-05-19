package org.example.pmas.exception;

public class CreateObjectException extends RuntimeException{

    public CreateObjectException(int id) {
        super("Service error: Something went wrong after creating object with id: " + id);
    }
}
