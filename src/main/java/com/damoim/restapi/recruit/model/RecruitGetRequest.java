package com.damoim.restapi.recruit.model;

import com.damoim.restapi.recruit.entity.QRecruit;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/02/27
 */
@Getter
@Builder
@AllArgsConstructor
public class RecruitGetRequest {

    @ApiModelProperty(value = "제목", example = "새로운 서비스를 함께 할 팀원을 모집합니다.")
    private String title;

    @ApiModelProperty(value = "내용", example = "B2B 서비스 고도화/안정화를 위하여 백엔드 개발자를 채용하고 있습니다!")
    private String description;

    @ApiModelProperty(value = "회사", example = "네이버")
    private String company;

    @ApiModelProperty(value = "근무지", example = "판교역")
    private String location;

    @ApiModelProperty(value = "채용보상금", example = "500000")
    private Integer reward;

    @ApiModelProperty(value = "태그", example = "[\"복지좋은회사\",\"인센티브\",\"포털회사\",\"트래픽많은\"]")
    private String[] tags;

    @ApiModelProperty(value = "등록자", example = "오성록")
    private String register;

}
