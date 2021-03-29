package com.damoim.restapi.bookreview.model;

import com.damoim.restapi.recruit.model.RecruitResponse;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class BookReviewsResponse {
    private Collection<BookReviewResponse> items;
    private int totalCount;

    public BookReviewsResponse(Collection<BookReviewResponse> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public BookReviewsResponse(BookReviewResponse item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
