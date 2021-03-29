package com.damoim.restapi.like.dao;

import lombok.Data;

/**
 * @author gisung.go
 * @since 2021-03-20
 */

@Data
public class BoardLikeSearchCondition {
    private Boolean status;
    private int likeCount;
    private String popularity;
}
