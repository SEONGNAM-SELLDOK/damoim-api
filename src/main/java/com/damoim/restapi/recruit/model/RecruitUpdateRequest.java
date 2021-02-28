package com.damoim.restapi.recruit.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author SeongRok.Oh
 * @since 2021/02/24
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecruitUpdateRequest extends RecruitSaveRequest {
    @ApiModelProperty(example = "1")
    @NotNull
    private Long id;
}
