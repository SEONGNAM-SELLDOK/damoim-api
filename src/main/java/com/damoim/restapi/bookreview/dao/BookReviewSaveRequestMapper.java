package com.damoim.restapi.bookreview.dao;

import com.damoim.restapi.bookreview.entity.BookReview;
import com.damoim.restapi.bookreview.model.BookReviewSaveRequest;
import com.damoim.restapi.config.jpa.EntityMapper;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@Mapper(componentModel = "spring")
public interface BookReviewSaveRequestMapper extends EntityMapper<BookReviewSaveRequest, BookReview> {
}
