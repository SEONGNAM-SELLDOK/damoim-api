package com.damoim.restapi.recruit.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/02/24
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecruitUpdateRequest {
    @ApiModelProperty(example = "1")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "등록자", required = true, example = "오성록")
    @NotEmpty
    private String register;

    @ApiModelProperty(value = "회사", required = true, example = "네이버")
    @NotEmpty
    private String company;

    @ApiModelProperty(value = "제목", required = true, example = "새로운 서비스를 함께 할 팀원을 모집합니다.")
    @NotEmpty
    private String title;

    @ApiModelProperty(value = "내용", example = "B2B 서비스 고도화/안정화를 위하여 백엔드 개발자를 채용하고 있습니다!")
    private String description;

    @ApiModelProperty(value = "마감일", required = true, example = "2021-02-28")
    @Future
    @NotNull
    private LocalDate deadline;

}
