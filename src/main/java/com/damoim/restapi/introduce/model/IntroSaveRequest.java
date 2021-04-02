package com.damoim.restapi.introduce.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IntroSaveRequest {

    @ApiModelProperty(value = "게시내용", required = true, example = "반갑습니다.")
    @NotEmpty
    private String content;
}
