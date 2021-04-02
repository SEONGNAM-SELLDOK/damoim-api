package com.damoim.restapi.introduce.model;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@Getter
public class IntroItemResponse {
    private Collection<IntroResponse> items;
    private int totalCount;


    public IntroItemResponse(Collection<IntroResponse> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public IntroItemResponse(IntroResponse item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }
}
