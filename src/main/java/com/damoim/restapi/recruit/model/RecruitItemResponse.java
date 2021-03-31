package com.damoim.restapi.recruit.model;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class RecruitItemResponse {
    private Collection<RecruitResponse> items;
    private int totalCount;

    public RecruitItemResponse(Collection<RecruitResponse> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public RecruitItemResponse(RecruitResponse item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
