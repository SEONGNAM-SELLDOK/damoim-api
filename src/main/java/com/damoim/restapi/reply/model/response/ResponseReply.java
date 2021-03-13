package com.damoim.restapi.reply.model.response;

import com.damoim.restapi.reply.entity.ChildReply;
import com.damoim.restapi.reply.entity.Reply;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class ResponseReply {

    private final Long boardId;
    private Reply reply;
    private ChildReply childReply;

    private ResponseReply(Long boardId, Reply reply) {
        this.boardId = boardId;
        this.reply = reply;
    }

    private ResponseReply(Long boardId, ChildReply childReply) {
        this.boardId = boardId;
        this.childReply = childReply;
    }

    public static ResponseReply of(Long boardId, Reply reply) {
        return new ResponseReply(boardId, reply);
    }

    public static ResponseReply of(Long boardId, ChildReply childReply) {
        return new ResponseReply(boardId, childReply);
    }
}
