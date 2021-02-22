package com.damoim.restapi.seminar.model;

import com.damoim.restapi.seminar.entity.DamoimTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Getter
@Setter
@Builder
public class SaveSeminarRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String image;
    @NotNull
    private String country; // 국가
    @NotNull
    private String city; // 도시
    @NotNull
    private String street; // 상세 주소
    @NotNull
    private String totalMember; // 총원
    @NotNull
    private String currentMember; //현재원
    @NotNull
    private String subject; // 주제
    @NotNull
    private DamoimTag damoimTag; // 태그
    @NotNull
    private LocalDateTime endDate; // 마감일

}
