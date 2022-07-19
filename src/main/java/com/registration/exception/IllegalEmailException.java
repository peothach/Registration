package com.registration.exception;

public class IllegalEmailException extends RuntimeException {
  public IllegalEmailException(String message) {
    super(message);
  }
}
