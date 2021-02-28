package com.damoim.restapi.recruit.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    @ApiModelProperty(value = "태그", example = "[\"복지좋은회사\",\"인센티브\",\"포털회사\",\"트래픽많은\"]")
    private String[] tags;

    @ApiModelProperty(value = "내용", example = "B2B 서비스 고도화/안정화를 위하여 백엔드 개발자를 채용하고 있습니다!")
    private String description;

    @ApiModelProperty(value = "마감일", required = true, example = "2021-02-28")
    @Future
    @NotNull
    private LocalDate deadline;

    @ApiModelProperty(value = "대표이미지", required = true)
    private MultipartFile file;

}
