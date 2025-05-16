package org.example.pmas.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(Throwable cause) {
        super("Database error: " + cause.getMessage());
    }

    public DatabaseException(String message, Throwable cause) {
        super(message + ": " + cause.getMessage());
    }
}
