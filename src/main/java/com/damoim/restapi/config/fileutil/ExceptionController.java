package com.damoim.restapi.config.fileutil;

import com.damoim.restapi.config.fileutil.error.ErrorMessage;
import com.damoim.restapi.config.fileutil.error.NotSupportedTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotSupportedTypeException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> notSupportedType(NotSupportedTypeException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorMessage(e));
    }
}
