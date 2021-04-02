package com.damoim.restapi.introduce.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@Getter
@Setter
@NoArgsConstructor
public class IntroUpdateRequest extends IntroSaveRequest {

    @ApiModelProperty(example = "1", required = true)
    @Min(1)
    private Long id;

    @Builder(builderMethodName = "updateBuilder")
    public IntroUpdateRequest(Long id, String content) {
        super(content);
        this.id = id;
    }
}
