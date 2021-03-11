package com.damoim.restapi.testReply;

import lombok.Getter;

@Getter
public class TResponseReply {

    private final Long boardId;
    private final TReply reply;

    private TResponseReply(Long boardId, TReply reply) {
        this.boardId = boardId;
        this.reply = reply;
    }

    public static TResponseReply of(Long boardId, TReply reply) {
        return new TResponseReply(boardId, reply);
    }
}
