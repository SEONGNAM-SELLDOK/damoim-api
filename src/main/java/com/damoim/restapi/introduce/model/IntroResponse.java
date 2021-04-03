package com.damoim.restapi.introduce.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@Getter
@Setter
public class IntroResponse {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(value = "내용", example = "반갑습니다.")
    private String content;
    @ApiModelProperty(value = "등록자", example = "오성록")
    private String register;
    @ApiModelProperty(value = "등록일", example = "2021-02-25T18:30:33.121Z")
    private LocalDateTime createdDate;
    @ApiModelProperty(value = "수정자", example = "홍길동")
    private String modifier;
    @ApiModelProperty(value = "수정일", example = "2021-02-25T18:30:33.121")
    private LocalDateTime modifiedDate;
}
