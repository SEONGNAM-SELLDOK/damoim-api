package com.damoim.restapi.recruit.model;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class RecruitItemWithReplyResponse {
    private Collection<RecruitResponseWithReply> items;
    private int totalCount;

    public RecruitItemWithReplyResponse(Collection<RecruitResponseWithReply> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public RecruitItemWithReplyResponse(RecruitResponseWithReply item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
