package com.damoim.restapi.secondhandtrade.model.reply;

import com.damoim.restapi.secondhandtrade.entity.reply.Reply;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestReply {

    private String writer;

    private String content;

    public Reply toEntity(UsedItem item) {
        return Reply.builder()
            .writer(writer)
            .content(content)
            .usedItem(item)
            .build();
    }
}
