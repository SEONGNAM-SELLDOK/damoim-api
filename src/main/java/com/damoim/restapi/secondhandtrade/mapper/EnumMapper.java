package com.damoim.restapi.secondhandtrade.mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnumMapper {

  private final Map<String, List<EnumValue>> factory = new HashMap<>();

  private List<EnumValue> toEnumValues(Class<? extends EnumMapperType> e) {
    return Arrays
        .stream(e.getEnumConstants())
        .map(EnumValue::new)
        .collect(Collectors.toList());
  }

  public void put(String key, Class<? extends EnumMapperType> e) {
    factory.put(key, toEnumValues(e));
  }

  public Map<String, List<EnumValue>> getAll() {
    return factory;
  }

  public Map<String, List<EnumValue>> get(String keys) {
    return Arrays
        .stream(keys.split(","))
        .collect(Collectors.toMap(Function.identity(), factory::get));
  }

}
