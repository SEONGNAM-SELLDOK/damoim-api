package com.damoim.restapi.reply.model.response;

import com.damoim.restapi.reply.entity.ChildReply;
import com.damoim.restapi.reply.entity.Reply;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
@Getter
@JsonInclude(Include.NON_NULL)
@Builder
public class ResponseReply {

    private final Long boardId;
    private final Reply reply;
    private final ChildReply childReply;

    public static ResponseReply of(Long boardId, Reply reply) {
        return ResponseReply.builder().boardId(boardId).reply(reply).build();
    }

    public static ResponseReply of(Long boardId, ChildReply childReply) {
        return ResponseReply.builder().boardId(boardId).childReply(childReply).build();
    }
}
