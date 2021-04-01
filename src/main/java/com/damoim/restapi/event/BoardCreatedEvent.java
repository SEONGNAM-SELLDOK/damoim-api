package com.damoim.restapi.event;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.event.boardcount.entity.BoardCount;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class BoardCreatedEvent {

    private final Long boardId;
    private final BoardType boardType;

    public BoardCount toEntity() {
        return BoardCount.builder()
            .boardType(boardType)
            .boardId(boardId)
            .build();
    }
}
