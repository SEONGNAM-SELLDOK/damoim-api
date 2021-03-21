package com.damoim.restapi.like.model;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author gisung.go
 * @since 2021-03-20
 */

@Getter
@ToString
@Builder
@NoArgsConstructor
public class SaveLikeRequest {
    @ApiModelProperty(value = "글 ID", example = "1")
    private Long boardId;
    @ApiModelProperty(value = "글 좋아요 ID", example = "1")
    private Long boardLikeId;
    @ApiModelProperty(value = "좋아요 상태", example = "true")
    private Boolean status;

    @QueryProjection
    public SaveLikeRequest(Long boardId, Long boardLikeId, Boolean status) {
        this.boardId = boardId;
        this.boardLikeId = boardLikeId;
        this.status = status;
    }
}
