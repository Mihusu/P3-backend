package com.skarp.prio.writeoffticket;

public class IncorrectManagerCodeException extends Exception {
  String message = "Incorrect manager code supplied";

  @Override
  public String getMessage() {
    return message;
  }
}
