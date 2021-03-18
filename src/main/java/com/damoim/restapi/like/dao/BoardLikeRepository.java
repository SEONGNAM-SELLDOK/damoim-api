package com.damoim.restapi.like.dao;

import com.damoim.restapi.like.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update BoardLike set boardCount = boardCount + 1  where boardId = :boardId")
    int addLikeCount(@Param("boardId") Long boardId);

    @Modifying(clearAutomatically = true)
    @Query("update BoardLike set boardCount = boardCount - 1 where boardId = :boardId")
    int subtractLikeCount(@Param("boardId") Long boardId);

    @Query("select b.boardCount from BoardLike b where b.boardId = :boardId")
    int getBoardCount(@Param("boardId") Long boardId);
}
