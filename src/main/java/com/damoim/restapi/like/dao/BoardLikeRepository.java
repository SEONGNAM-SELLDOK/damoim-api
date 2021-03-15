package com.damoim.restapi.like.dao;

import com.damoim.restapi.like.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    @Query("update BoardLike l set l.boardCount = l.boardCount + 1  where l.id = :id")
    int addLikeCount(Long id);

    @Query("update BoardLike l set l.boardCount = l.boardCount - 1 where l.id = :id")
    int subtractLikeCount(Long id);
}
