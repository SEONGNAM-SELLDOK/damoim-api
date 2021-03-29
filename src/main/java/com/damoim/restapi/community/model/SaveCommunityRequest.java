package com.damoim.restapi.community.model;


import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "제목", example = "1년차 연봉이 궁금합니다.")
    private String title;
    @NotBlank
    @ApiModelProperty(value = "내용", example = "자세한 정보는 블라인드 또는 잡플래닛에서 검색해보세요.")
    private String content;

    @QueryProjection
    public SaveCommunityRequest(@NotBlank String title, @NotBlank String content) {
        this.title = title;
        this.content = content;
    }
}
