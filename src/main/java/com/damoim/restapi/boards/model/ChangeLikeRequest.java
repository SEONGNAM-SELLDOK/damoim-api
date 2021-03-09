package com.damoim.restapi.boards.model;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeLikeRequest {
    private Long id;
    private String boardLike;
}
