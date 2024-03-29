package com.damoim.restapi.lecture.model;

import com.damoim.restapi.lecture.entity.LectureSubject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * @author SeongRok.Oh
 * @since 2021/03/10
 */
@Getter
@Setter
@NoArgsConstructor
public class LectureUpdateRequest extends LectureSaveRequest {

    @ApiModelProperty(example = "1")
    @Min(1)
    private long id;

    @Builder(builderMethodName = "updateRequestBuilder")
    public LectureUpdateRequest(long id, String title, String description, String speaker, LectureSubject subject, String route, LocalDate deadline) {
        super(title, description, speaker, subject, route, deadline);
        this.id = id;
    }
}
