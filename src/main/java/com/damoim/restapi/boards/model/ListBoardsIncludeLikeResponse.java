package com.damoim.restapi.boards.model;

import com.damoim.restapi.like.model.ListLikeResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * @author gisung.go
 * @since 2021-03-24
 */

@Getter
public class ListBoardsIncludeLikeResponse {
    private final Page<ListBoardsResponse> listBoardsResponses;
    private final Page<ListLikeResponse> listLikeResponses;

    private ListBoardsIncludeLikeResponse(Page<ListBoardsResponse> listBoardsResponses, Page<ListLikeResponse> listLikeResponses) {
        this.listBoardsResponses = listBoardsResponses;
        this.listLikeResponses = listLikeResponses;
    }

    public static ListBoardsIncludeLikeResponse toMapper(Page<ListBoardsResponse> listBoardsResponses, Page<ListLikeResponse> listLikeResponses) {
        return new ListBoardsIncludeLikeResponse(listBoardsResponses, listLikeResponses);
    }
}
