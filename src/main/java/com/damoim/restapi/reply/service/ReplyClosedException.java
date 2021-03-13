package com.damoim.restapi.reply.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReplyClosedException extends RuntimeException {

    private final Long inputValue;


}
