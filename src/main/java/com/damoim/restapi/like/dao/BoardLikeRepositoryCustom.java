package com.damoim.restapi.like.dao;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.like.model.ListLikeResponse;
import com.damoim.restapi.like.model.ReadLikeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author gisung.go
 * @since 2021-03-20
 */
public interface BoardLikeRepositoryCustom {
    List<ReadLikeResponse> findByLikeInfo(Long boardId, BoardType type);
    Page<ListLikeResponse> searchBoardLike(BoardLikeSearchCondition condition, Pageable pageable);
}
