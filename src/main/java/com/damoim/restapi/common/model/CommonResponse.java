package com.damoim.restapi.common.model;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

/**
 * @author SeongRok.Oh
 * @since 2021/03/29
 */
@Getter
public class CommonResponse<T> {
    private Collection<T> items;
    private int totalCount;

    public CommonResponse(Collection<T> items) {
        this.items = items;
        this.totalCount = items.size();
    }

    public CommonResponse(T item) {
        this.items = Collections.singleton(item);
        this.totalCount = 1;
    }

}
