package com.damoim.restapi.reply.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
@AllArgsConstructor
@Getter
public class ReplyClosedException extends RuntimeException {

    private final Long inputValue;


}
