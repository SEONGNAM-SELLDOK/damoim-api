package com.damoim.restapi.secondhandtrade.errormsg;

public class NotFoundPageException extends RuntimeException {

  private final String value;

  public NotFoundPageException(String message, String value) {
    super(message);
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
