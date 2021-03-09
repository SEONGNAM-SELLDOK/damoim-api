package com.damoim.restapi.secondhandtrade.model.reply;

import com.damoim.restapi.secondhandtrade.entity.reply.Reply;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestReply {

    @Min(1)
    private Long no;

    @NotEmpty
    private String writer;

    @NotEmpty
    private String content;

    public Reply toEntity(UsedItem item) {
        return Reply.builder()
            .level(1)
            .writer(writer)
            .content(content)
            .usedItem(item)
            .build();
    }
}
