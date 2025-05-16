package org.example.pmas.exception;

public class DeleteObjectException extends RuntimeException {
  public DeleteObjectException(int id) {
    super("service error: Couldn't delete object with id: " + id);
  }
}
