package com.damoim.restapi.reply.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
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
