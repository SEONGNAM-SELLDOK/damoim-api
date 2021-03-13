package com.damoim.restapi.config.errorhandler.message;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiMessage {

  private final int statusCode;

  private final String message;

  private final String inputValue;

}
