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
public class BookReviewItemResponse {
    private Collection<BookReviewResponse> items;
    private int totalCount;

    public BookReviewItemResponse(Collection<BookReviewResponse> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public BookReviewItemResponse(BookReviewResponse item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
