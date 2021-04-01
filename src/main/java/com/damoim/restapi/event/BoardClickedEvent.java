package com.damoim.restapi.event;

import com.damoim.restapi.boards.entity.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BoardClickedEvent {

    private final Long boardId;
    private final BoardType boardType;
}
