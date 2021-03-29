package com.damoim.restapi.recruit.model;

import com.damoim.restapi.lecture.model.LectureResponse;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class RecruitsResponse {
    private Collection<RecruitResponse> items;
    private int totalCount;

    public RecruitsResponse(Collection<RecruitResponse> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public RecruitsResponse(RecruitResponse item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
