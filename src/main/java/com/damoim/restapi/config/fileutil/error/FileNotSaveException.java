package com.damoim.restapi.config.fileutil.error;

import org.springframework.web.bind.annotation.ResponseStatus;


public class FileNotSaveException extends RuntimeException {

    public FileNotSaveException(String message) {
        super(message);
    }

    public FileNotSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
