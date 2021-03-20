package com.damoim.restapi.config.errorhandler.message;

import com.damoim.restapi.config.fileutil.error.NotSupportedTypeException;
import java.util.List;
import lombok.Getter;

@Getter
public class TypeErrorMessage {

    private final String inputType;
    private final List<String> supType;
    private final int status;

    public TypeErrorMessage(NotSupportedTypeException e) {
        this.inputType = e.getContentType();
        this.supType = e.getSupType();
        this.status = e.getStatus();
    }
}
