package com.damoim.restapi.event.boardcount.dao;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.event.boardcount.entity.BoardCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardCountRepository extends JpaRepository<BoardCount, Long> {

    BoardCount findByBoardIdAndBoardType(Long boardId, BoardType boardType);

    @Modifying
    @Query("update BoardCount  b set b.clickCount = b.clickCount+1 "
        + "where  b.boardId =:boardId and b.boardType =:boardType")
    void updateCount(Long boardId, BoardType boardType);
}
