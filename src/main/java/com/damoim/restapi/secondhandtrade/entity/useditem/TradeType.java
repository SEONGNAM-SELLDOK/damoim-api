package com.damoim.restapi.secondhandtrade.entity.useditem;

import com.damoim.restapi.secondhandtrade.mapper.EnumMapperType;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum TradeType implements EnumMapperType {
    ALL("택배,직거래"), DELIVERY("택배"), DIRECT("직거래");

    private final String value;

    TradeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TradeType fromJson(String key) {
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
