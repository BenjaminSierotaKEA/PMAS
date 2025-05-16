package org.example.pmas.exception;

public class DeleteObjectException extends RuntimeException {
  public DeleteObjectException(int id) {
    super("service error: Something went wrong after deleting object with id: " + id);
  }
}
