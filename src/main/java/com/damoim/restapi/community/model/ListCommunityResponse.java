package com.damoim.restapi.community.model;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author gisung.go
 * @since 2021-03-19
 */

@Data
@Builder
@NoArgsConstructor
public class ListCommunityResponse {
    @ApiModelProperty(example = "1")
    private long id;
    @ApiModelProperty(value = "제목", example = "1년차 연봉이 궁금합니다.")
    private String title;
    @ApiModelProperty(value = "내용", example = "자세한 정보는 블라인드 또는 잡플래닛에서 검색해보세요.")
    private String content;
    @ApiModelProperty(value = "작성일", example = "2021-02-21T20:17:22.137Z")
    private LocalDateTime createDate;
    @ApiModelProperty(value = "수정일", example = "2021-03-16T20:17:22.137Z")
    private LocalDateTime updateDate;

    @QueryProjection
    public ListCommunityResponse(long id, String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
