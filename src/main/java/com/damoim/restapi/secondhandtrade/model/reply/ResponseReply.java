package com.damoim.restapi.secondhandtrade.model.reply;

import com.damoim.restapi.secondhandtrade.entity.reply.Reply;
import lombok.Getter;

@Getter
public class ResponseReply {

    private final Long boardId;
    private final Reply reply;

    private ResponseReply(Long boardId, Reply reply) {
        this.boardId = boardId;
        this.reply = reply;
    }

    public static ResponseReply of(Long boardId, Reply reply) {
        return new ResponseReply(boardId, reply);
    }
}
