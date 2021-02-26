package com.damoim.restapi.boards.dao;

import com.damoim.restapi.boards.model.ListBoardsResponse;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

public interface BoardsRepositoryCustom {
    List<ReadBoardsResponse> findByBoardInfo(Long id);
    Page<ListBoardsResponse> searchBoard(BoardsSearchCondition condition, Pageable pageable);


}
