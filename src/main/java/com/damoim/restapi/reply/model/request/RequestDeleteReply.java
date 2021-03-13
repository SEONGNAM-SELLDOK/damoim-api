package com.damoim.restapi.reply.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeleteReply {

    @Min(1)
    @NotNull
    private Long replyId;

    @NotNull
    private Boolean isChildId;

    public boolean isParentId() {
        return !isChildId;
    }

    public boolean isChildReplyId() {
        return isChildId;
    }
}
