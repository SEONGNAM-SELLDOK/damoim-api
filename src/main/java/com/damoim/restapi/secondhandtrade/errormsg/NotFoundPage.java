package com.damoim.restapi.secondhandtrade.errormsg;

public class NotFoundPage extends RuntimeException {

  private final String value;

  public NotFoundPage(String message, String value) {
    super(message);
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
