package com.damoim.restapi.secondhandtrade.errormsg;

public class NotFoundResource extends RuntimeException {

    private final String value;

    public NotFoundResource(String message, String value) {
        super(message);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
