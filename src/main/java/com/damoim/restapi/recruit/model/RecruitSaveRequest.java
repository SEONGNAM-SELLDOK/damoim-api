package com.damoim.restapi.recruit.model;

import com.damoim.restapi.recruit.entity.RecruitTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecruitSaveRequest {

    @ApiModelProperty(value = "등록자", required = true, example = "오성록")
    @NotBlank
    private String register;

    @ApiModelProperty(value = "회사", required = true, example = "네이버")
    @NotBlank
    private String company;

    @ApiModelProperty(value = "제목", required = true, example = "새로운 서비스를 함께 할 팀원을 모집합니다.")
    @NotBlank
    private String title;

    @ApiModelProperty(value = "근무지", required = true, example = "판교역")
    @NotBlank
    private String location;

    @ApiModelProperty(value = "채용보상금", required = true, example = "500000")
    @NotNull
    @Min(0)
    private Integer reward;

    @ApiModelProperty(value = "태그", example = "JAVA")
    private RecruitTag tag;

    @ApiModelProperty(value = "내용", example = "B2B 서비스 고도화/안정화를 위하여 백엔드 개발자를 채용하고 있습니다!")
    private String description;

    @ApiModelProperty(value = "마감일", required = true, example = "2021-02-28")
    @Future
    @NotNull
    private LocalDate deadline;

    @ApiModelProperty(value = "이미지주소", required = true)
    private String image;

}
