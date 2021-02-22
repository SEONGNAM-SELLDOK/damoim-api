package com.damoim.restapi.secondhandtrade.errormsg;

public class NotFoundPage extends RuntimeException {

  private final Object value;

  public NotFoundPage(String message, Object value) {
    super(message);
    this.value = value;
  }

  public Object getValue() {
    return value;
  }
}
