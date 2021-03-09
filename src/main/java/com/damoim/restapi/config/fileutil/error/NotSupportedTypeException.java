package com.damoim.restapi.config.fileutil.error;

import java.util.List;
import lombok.Getter;

@Getter
public class NotSupportedTypeException extends RuntimeException {

    private final String contentType;
    private final List<String> supType;
    private final int status;

    public NotSupportedTypeException(String contentType, List<String> supType, int status) {
        this.contentType = contentType;
        this.supType = supType;
        this.status = status;
    }
}
