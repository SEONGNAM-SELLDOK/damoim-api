package com.damoim.restapi.bookreview.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@Setter
@Getter
@NoArgsConstructor
public class BookReviewUpdateRequest extends BookReviewSaveRequest {
    @ApiModelProperty(value = "아이디", required = true, example = "1")
    @Min(1)
    private long id;

    @Builder(builderMethodName = "updateBookReviewBuilder")
    public BookReviewUpdateRequest(long id, String title, String description, String image, String isbn, String publisher, String writer, String subject, Set<String> tag, LocalDate deadline) {
        super(title, description, image, isbn, publisher, writer, subject, tag, deadline);
        this.id = id;
    }

}
