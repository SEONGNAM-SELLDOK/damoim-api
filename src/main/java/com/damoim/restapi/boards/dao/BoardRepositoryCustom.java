package com.damoim.restapi.boards.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.model.ListBoardsResponse;
import com.damoim.restapi.boards.model.ReadBoardsResponse;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

public interface BoardRepositoryCustom {
    List<ReadBoardsResponse> findByBoardInfo(Long id, BoardType type);

    Page<ListBoardsResponse> searchBoard(BoardSearchCondition condition, Pageable pageable);

}
