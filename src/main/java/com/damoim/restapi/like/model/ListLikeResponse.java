package com.damoim.restapi.like.model;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author gisung.go@innowireless.com
 * @since 2021-03-22
 */

@Getter
@Setter
@NoArgsConstructor
public class ListLikeResponse {
    @ApiModelProperty(value = "글 ID", example ="1")
    private long boardId;
    @ApiModelProperty(value = "좋아요 상태", example ="true")
    private Boolean status;
    @ApiModelProperty(value = "좋아요 총수", example ="100")
    private int boardCount;

    @QueryProjection
    public ListLikeResponse(long boardId, Boolean status, int boardCount) {
        this.boardId = boardId;
        this.status = status;
        this.boardCount = boardCount;
    }
}
