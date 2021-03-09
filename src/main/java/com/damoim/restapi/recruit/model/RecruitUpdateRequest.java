package com.damoim.restapi.recruit.model;

import com.damoim.restapi.recruit.entity.RecruitTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author SeongRok.Oh
 * @since 2021/02/24
 */
@NoArgsConstructor
@Setter
@Getter
public class RecruitUpdateRequest extends RecruitSaveRequest {
    @ApiModelProperty(example = "1")
    @NotNull
    private Long id;

    @Builder(builderMethodName = "updateRequestBuilder")
    private RecruitUpdateRequest(Long id, String register, String company, String title, String location, Integer reward, RecruitTag tag, String description, LocalDate deadline, String image) {
        super(register, company, title, location, reward, tag, description, deadline, image);
        this.id = id;
    }
}
