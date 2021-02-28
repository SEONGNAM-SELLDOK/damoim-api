package com.damoim.restapi.secondhandtrade.mapper;

public class EnumValue {

  private final String key;
  private final String value;

  public EnumValue(EnumMapperType enumMapperType) {
    key = enumMapperType.getCode();
    value = enumMapperType.getTitle();
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}
