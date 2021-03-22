package com.damoim.restapi.like.model;

import com.damoim.restapi.boards.entity.BoardType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeLikeRequest {
    @ApiModelProperty(value = "boardLikeId", example = "1")
    private Long boardLikeId;
    @ApiModelProperty(value = "보드 ID", example = "1")
    private Long boardId;
    @ApiModelProperty(value = "보드 타입", example = "SEMINAR")
    private BoardType boardType;
//    @ApiModelProperty(value = "좋아요 ID", example = "1")
//    private Long boardLikeId;
    @ApiModelProperty(value = "좋아요 상태", example = "true")
    private Boolean status;
}
