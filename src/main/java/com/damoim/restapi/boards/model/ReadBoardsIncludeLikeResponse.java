package com.damoim.restapi.boards.model;

import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.like.model.ReadLikeResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author gisung.go
 * @since 2021-03-24
 */

@Getter
public class ReadBoardsIncludeLikeResponse {
    private final List<ReadBoardsResponse> readBoardsResponses;
    private final List<ReadLikeResponse> readLikeResponses;

    private  ReadBoardsIncludeLikeResponse(List<ReadBoardsResponse> readBoardsResponses, List<ReadLikeResponse> readLikeResponses) {
        this.readBoardsResponses = readBoardsResponses;
        this.readLikeResponses = readLikeResponses;
    }

    public static ReadBoardsIncludeLikeResponse toMapper(List<ReadBoardsResponse> readBoardsResponses, List<ReadLikeResponse> readLikeResponses) {
        return new ReadBoardsIncludeLikeResponse(readBoardsResponses, readLikeResponses);
    }
}
