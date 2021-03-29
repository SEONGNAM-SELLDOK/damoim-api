package com.damoim.restapi.lecture.model;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class LectureItemResponse {
    private Collection<LectureResponse> items;
    private int totalCount;

    public LectureItemResponse(Collection<LectureResponse> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public LectureItemResponse(LectureResponse item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
