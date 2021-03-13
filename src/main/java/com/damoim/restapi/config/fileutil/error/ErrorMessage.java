package com.damoim.restapi.config.fileutil.error;

import java.util.List;
import lombok.Getter;

@Getter
public class ErrorMessage {

    private final String inputType;
    private final List<String> supType;
    private final int status;

    public ErrorMessage(NotSupportedTypeException e) {
        this.inputType = e.getContentType();
        this.supType = e.getSupType();
        this.status = e.getStatus();
    }
}
