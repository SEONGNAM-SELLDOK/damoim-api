package com.damoim.restapi.community.model;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author Gisung Go
 * @since 2021.03.18
 * */

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ReadCommunityResponse {
    @ApiModelProperty(example = "1")
    private long id;
    @NotBlank
    @ApiModelProperty(value = "제목", example = "1년차 연봉이 궁금합니다.")
    private String title;
    @NotBlank
    @ApiModelProperty(value = "내용", example = "자세한 정보는 블라인드 또는 잡플래닛에서 검색해보세요.")
    private String content;
    @ApiModelProperty(value = "작성일", example = "2021-02-21T20:17:22.137Z")
    private LocalDateTime createDate;
    @ApiModelProperty(value = "수정일", example = "2021-03-16T20:17:22.137Z")
    private LocalDateTime updateDate;

    @QueryProjection
    public ReadCommunityResponse(long id, String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
