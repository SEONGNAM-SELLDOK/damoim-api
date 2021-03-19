package com.damoim.restapi.community.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author gisung.go
 * @since 2021-03-19
 */

@Data
@Builder
@NoArgsConstructor
public class ListCommunityResponse {
    private String title;
    private String content;

    @QueryProjection
    public ListCommunityResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
