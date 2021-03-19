package com.damoim.restapi.community.model;

import com.damoim.restapi.community.entity.Community;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author gisung.go
 * @since 2021-03-19
 */

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModifyCommunityRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public Community updateTo(Community community) {
        community.setTitle(title);
        community.setContent(content);
        return community;
    }
}
