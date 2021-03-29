package com.damoim.restapi.bookreview.model;

import com.damoim.restapi.lecture.dao.LectureResponseWithReply;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class BookReviewsWithReplyResponse {
    private Collection<BookReviewResponseWithReply> items;
    private int totalCount;

    public BookReviewsWithReplyResponse(Collection<BookReviewResponseWithReply> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public BookReviewsWithReplyResponse(BookReviewResponseWithReply item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
