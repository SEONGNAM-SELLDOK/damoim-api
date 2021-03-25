package com.damoim.restapi.bookreview.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/03/22
 */
@Getter
@Setter
public class BookReviewResponse {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(value = "제목", example = "객체지향의 사실과 오해를 읽고")
    private String title;
    @ApiModelProperty(value = "내용", example = "아주 좋았다.")
    private String description;
    @ApiModelProperty(value = "이미지주소", example = "/bookreview/oop.jpg")
    private String image;
    @ApiModelProperty(value = "국제 표준 도서 번호", example = "978-89-98139-76-6")
    private String isbn;
    @ApiModelProperty(value = "출판사", example = "위키북스")
    private String publisher;
    @ApiModelProperty(value = "지은이", example = "조영호")
    private String writer;
    @ApiModelProperty(value = "주제", example = "객체지향")
    private String subject;
    @ApiModelProperty(value = "태그", example = "SOLID")
    private Set<String> tag;
    @ApiModelProperty(value = "마감일", example = "2021-03-16")
    private LocalDate deadline;
    @ApiModelProperty(value = "생성일", example = "2021-03-16")
    private LocalDateTime createdDate;
    @ApiModelProperty(value = "등록자", example = "오성록")
    private String register;
    @ApiModelProperty(value = "수정일", example = "2021-03-16")
    private LocalDateTime modifiedDate;
    @ApiModelProperty(value = "수정자", example = "홍길동")
    private String modifier;
}
