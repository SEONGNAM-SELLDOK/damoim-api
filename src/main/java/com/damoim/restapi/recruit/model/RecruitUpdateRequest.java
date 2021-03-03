package com.damoim.restapi.recruit.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    private RecruitUpdateRequest(Long id, String register, String company, String title, String location, Integer reward, String[] tags, String description, LocalDate deadline, String image) {
        super(register, company, title, location, reward, tags, description, deadline, image);
        this.id = id;
    }
}
