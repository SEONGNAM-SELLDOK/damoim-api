package com.damoim.restapi.bookreview.dao;

import com.damoim.restapi.bookreview.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
}
