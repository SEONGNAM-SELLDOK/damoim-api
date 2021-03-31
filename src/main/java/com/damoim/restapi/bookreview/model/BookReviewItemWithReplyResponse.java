package com.damoim.restapi.bookreview.model;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class BookReviewItemWithReplyResponse {
    private Collection<BookReviewResponseWithReply> items;
    private int totalCount;

    public BookReviewItemWithReplyResponse(Collection<BookReviewResponseWithReply> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public BookReviewItemWithReplyResponse(BookReviewResponseWithReply item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
