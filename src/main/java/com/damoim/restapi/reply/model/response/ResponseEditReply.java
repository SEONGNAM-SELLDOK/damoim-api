package com.damoim.restapi.reply.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseEditReply {

    private final Long id;
    private final String content;

    public static ResponseEditReply of(Long id, String content) {
        return ResponseEditReply.builder().id(id).content(content).build();
    }
}
