package com.damoim.restapi.bookreview.exception;

import org.springframework.http.HttpStatus;

/**
 * @author SeongRok.Oh
 * @since 2021/04/01
 */
public class UnauthorizedException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UnauthorizedException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}
