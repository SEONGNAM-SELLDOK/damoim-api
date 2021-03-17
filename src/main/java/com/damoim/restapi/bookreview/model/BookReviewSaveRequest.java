package com.damoim.restapi.bookreview.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewSaveRequest {
    @ApiModelProperty(value = "제목", required = true, example = "객체지향의 사실과 오해를 읽고")
    @NotEmpty
    private String title;
    @ApiModelProperty(value = "내용", example = "아주 좋았다.")
    private String description;
    @ApiModelProperty(value = "이미지 주소", example = "/bookreview/image01.jpg")
    private String image;
    @ApiModelProperty(value = "국제 표준 도서 번호", required = true, example = "978-89-98139-76-6")
    @NotEmpty
    private String isbn;
    @ApiModelProperty(value = "출판사", required = true, example = "위키북스")
    @NotEmpty
    private String publisher;
    @ApiModelProperty(value = "지은이", required = true, example = "조영호")
    @NotEmpty
    private String writer;
    @ApiModelProperty(value = "주제", required = true, example = "객체지향")
    @NotEmpty
    private String subject;
    @ApiModelProperty(value = "태그", example = "[\"OOP\",\"객체\",\"SOLID\"]")
    private Set<String> tag;
    @ApiModelProperty(value = "모임 마감일", required = true, example = "2022-03-16")
    @NotNull
    @Future
    private LocalDate deadline;
}
