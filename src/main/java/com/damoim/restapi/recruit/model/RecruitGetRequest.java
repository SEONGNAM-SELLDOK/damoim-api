package com.damoim.restapi.recruit.model;

import com.damoim.restapi.recruit.entity.Recruit;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author SeongRok.Oh
 * @since 2021/02/27
 */
@Builder
@AllArgsConstructor
public clasRecruitGetRequest implements Predicate<Recruit> {

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

    /*
     * 제목 - eq
     * 상세내용 - like
     * 회사 - like
     * 근무지 - eq
     * 채용 보상금 - 조회금액보다 작거나 같음
     * 태그 - eq
     * 등록자 - like
     */
    @Override
    public boolean test(Recruit recruit) {
        if (Objects.isNull(recruit)) {
            return true;
        }

        String title = recruit.getTitle();
        String description = recruit.getDescription();
        String company = recruit.getCompany();
        String location = recruit.getLocation();
        Integer reward = recruit.getReward();
        String[] tags = recruit.getTags();
        String register = recruit.getRegister();

        return (Objects.nonNull(title) && Objects.nonNull(this.title) && title.equals(this.title))
                || (Objects.nonNull(description) && Objects.nonNull(this.description) && description.contains(this.description))
                || (Objects.nonNull(company) && Objects.nonNull(this.company) && company.contains(this.company))
                || (Objects.nonNull(location) && Objects.nonNull(this.location) && location.equals(this.location))
                || (Objects.nonNull(reward) && Objects.nonNull(this.reward) && reward <= this.reward)
                || (Objects.nonNull(tags) && Objects.nonNull(this.tags) && containTag(tags, this.tags))
                || (Objects.nonNull(register) && Objects.nonNull(this.register) && register.contains(this.register))
                ;
    }

    private boolean containTag(String[] tags, String[] lookUpTags) {
        return lookUpTags.length == Arrays.stream(lookUpTags).filter(lookUpTag -> Arrays.asList(tags).contains(lookUpTag)).count();
    }
}
