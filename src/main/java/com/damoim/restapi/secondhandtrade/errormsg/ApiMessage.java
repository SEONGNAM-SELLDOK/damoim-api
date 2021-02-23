package com.damoim.restapi.secondhandtrade.errormsg;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiMessage {
  private final int statusCode;

  private final String message;

  private final String InputValue;

}
