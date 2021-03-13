package com.damoim.restapi.config.errorhandler;

import com.damoim.restapi.config.errorhandler.message.ApiMessage;
import com.damoim.restapi.config.errorhandler.message.TypeErrorMessage;
import com.damoim.restapi.config.fileutil.error.NotSupportedTypeException;
import com.damoim.restapi.reply.service.ReplyClosedException;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotSupportedTypeException.class)
    @ResponseBody
    public ResponseEntity<TypeErrorMessage> notSupportedType(NotSupportedTypeException e) {
        return ResponseEntity.status(e.getStatus()).body(new TypeErrorMessage(e));
    }

    @ExceptionHandler(NotFoundPageException.class)
    public ResponseEntity<ApiMessage> notFoundException(
        NotFoundPageException notFoundPageException) {
        ApiMessage apiMessage = ApiMessage.builder()
            .message(notFoundPageException.getMessage())
            .inputValue(notFoundPageException.getValue())
            .statusCode(HttpStatus.NOT_FOUND.value())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiMessage);
    }

    @ExceptionHandler(ReplyClosedException.class)
    public ResponseEntity<ApiMessage> replySaveException(
        ReplyClosedException replyClosedException) {
        ApiMessage apiMessage = ApiMessage.builder()
            .message("삭제된 댓글입니다.")
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .inputValue(String.valueOf(replyClosedException.getInputValue()))
            .build();
        return ResponseEntity.badRequest().body(apiMessage);
    }
}
