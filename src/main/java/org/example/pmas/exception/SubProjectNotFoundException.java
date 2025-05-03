package org.example.pmas.exception;

public class SubProjectNotFoundException extends RuntimeException {

    public SubProjectNotFoundException(int id) {
        super("SubProject med ID" + id + " var ikke fundet");
    }
}
