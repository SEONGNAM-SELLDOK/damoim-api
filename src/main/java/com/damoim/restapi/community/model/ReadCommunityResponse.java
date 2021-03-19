package com.damoim.restapi.community.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Gisung Go
 * @since 2021.03.18
 * */

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ReadCommunityResponse {
    private long id;
    private String title;
    private String content;


    @QueryProjection
    public ReadCommunityResponse(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
