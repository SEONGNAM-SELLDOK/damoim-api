package com.damoim.restapi.lecture.model;

import com.damoim.restapi.lecture.dao.LectureResponseWithReply;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class LectureItemWithReplyResponse {
    private Collection<LectureResponseWithReply> items;
    private int totalCount;

    public LectureItemWithReplyResponse(Collection<LectureResponseWithReply> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public LectureItemWithReplyResponse(LectureResponseWithReply item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
