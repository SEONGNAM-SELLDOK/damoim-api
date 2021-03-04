package com.damoim.restapi.secondhandtrade.entity.useditem;

import com.damoim.restapi.secondhandtrade.mapper.EnumMapperType;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category implements EnumMapperType {
    //추후 카테고리 추가
    DEFAULT("기타"),
    ELECTRONICS("전자제품"),
    FOOD("음식"),
    CLOTHES("옷"),
    BOOK("책");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Category fromJson(String key) {
        return valueOf(key.toUpperCase());
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return getValue();
    }
}
