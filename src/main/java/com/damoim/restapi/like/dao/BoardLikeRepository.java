package com.damoim.restapi.like.dao;

import com.damoim.restapi.like.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author gisung.go
 * @since 2021-03-20
 */

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>, BoardLikeRepositoryCustom {
//     boarlike 카운도 +, likeStatus true
    @Modifying(clearAutomatically = true)
    @Query("update BoardLike set likeCount = likeCount + 1 where boardId = :boardId")
    int addLikeCount(@Param("boardId") Long boardId);

    @Modifying(clearAutomatically = true)
    @Query("update BoardLike set likeCount = likeCount - 1 where boardId = :boardId")
    int subtractLikeCount(@Param("boardId") Long boardId);

    @Query("SELECT b.likeCount FROM BoardLike b WHERE b.boardId = :boardId")
    int getLikeCount(@Param("boardId") Long boardId);
}
