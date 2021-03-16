package com.damoim.restapi.reply.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEditReply {

    @NotEmpty
    private String writer;
    @NotEmpty
    private String content;
    @NotNull
    private Boolean isParentReply;

}
