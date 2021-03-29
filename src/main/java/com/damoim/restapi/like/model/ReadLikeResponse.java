package com.damoim.restapi.like.model;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadLikeResponse {
    @ApiModelProperty(value = "boardLikeId", example = "1")
    private Long boardLikeId;
    @ApiModelProperty(value = "likeStatusId", example = "1")
    private Long likeStatusId;
    @ApiModelProperty(value = "글 ID", example ="1")
    private long boardId;
    @ApiModelProperty(value = "좋아요 상태", example ="true")
    private Boolean status;
    @ApiModelProperty(value = "좋아요 총수", example ="100")
    private int likeCount;

    @QueryProjection
    public ReadLikeResponse(Long boardLikeId, Long likeStatusId, long boardId, Boolean status, int likeCount) {
        this.boardLikeId = boardLikeId;
        this.likeStatusId = likeStatusId;
        this.boardId = boardId;
        this.status = status;
        this.likeCount = likeCount;
    }
}
