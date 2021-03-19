package com.damoim.restapi.community.model;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author gisung go
 * @since 2021.03.18
 * */

@Getter
@Setter
@Builder
public class SaveCommunityRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @QueryProjection
    public SaveCommunityRequest(@NotBlank String title, @NotBlank String content) {
        this.title = title;
        this.content = content;
    }
}
