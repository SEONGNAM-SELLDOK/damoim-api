package com.damoim.restapi.community.model;

import com.damoim.restapi.community.entity.Community;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "제목", example = "1년차 연봉이 궁금합니다.")
    private String title;
    @NotBlank
    @ApiModelProperty(value = "내용", example = "자세한 정보는 블라인드 또는 잡플래닛에서 검색해보세요.")
    private String content;

    public Community updateTo(Community community) {
        community.setTitle(title);
        community.setContent(content);
        return community;
    }
}
