package com.damoim.restapi.like.model;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeLikeRequest {
    private Long boardId;
    private String boardLike;
}
