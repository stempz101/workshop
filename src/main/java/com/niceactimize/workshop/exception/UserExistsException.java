package com.niceactimize.workshop.exception;

public class UserExistsException extends RuntimeException {

  public UserExistsException(Reason reason) {
    super(reason.message);
  }

  public enum Reason {
    USERNAME("User is already exists by specified username"),
    EMAIL("User is already exists by specified email"),
    MOBILE("User is already exists by specified mobile number");

    private final String message;

    Reason(String message) {
      this.message = message;
    }
  }
}
