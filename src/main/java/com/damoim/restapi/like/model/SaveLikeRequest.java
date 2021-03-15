package com.damoim.restapi.like.model;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveLikeRequest {
    int likeCount;
    Long boardId;
}
