package com.damoim.restapi.bookreview.dao;

import com.damoim.restapi.bookreview.entity.BookReview;
import com.damoim.restapi.bookreview.model.BookReviewUpdateRequest;
import com.damoim.restapi.config.jpa.EntityMapper;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@Mapper(componentModel = "spring")
public interface BookReviewUpdateRequestMapper extends EntityMapper<BookReviewUpdateRequest, BookReview> {
}
