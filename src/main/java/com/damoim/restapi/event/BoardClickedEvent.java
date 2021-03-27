package com.damoim.restapi.event;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.event.boardcount.entity.BoardCount;
import lombok.Getter;


@Getter
public class BoardClickedEvent {

    private final Long boardId;
    private final BoardType boardType;

    public BoardClickedEvent(Long boardId, BoardType boardType) {
        this.boardId = boardId;
        this.boardType = boardType;
    }

    public BoardCount toEntity() {
        return BoardCount.builder()
            .boardType(boardType)
            .boardId(boardId)
            .build();
    }
}
