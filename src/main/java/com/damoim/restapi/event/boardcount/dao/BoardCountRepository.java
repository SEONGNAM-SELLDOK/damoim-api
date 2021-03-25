package com.damoim.restapi.event.boardcount.dao;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.event.boardcount.entity.BoardCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCountRepository extends JpaRepository<BoardCount, Long> {

    BoardCount findByBoardIdAndBoardType(Long boardId, BoardType boardType);
}
