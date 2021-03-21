package com.damoim.restapi.like.dao;

import com.damoim.restapi.like.entity.BoardLike;
import com.damoim.restapi.like.entity.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gisung.go
 * @since 2021-03-20
 */

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>, BoardLikeRepositoryCustom{
//     boarlike 카운도 +, likeStatus true
    @Modifying(clearAutomatically = true)
    @Query("update BoardLike set boardCount = boardCount + 1 where boardId = :boardId")
    int addLikeCount(@Param("boardId") Long boardId);

    @Modifying(clearAutomatically = true)
    @Query("update BoardLike set boardCount = boardCount - 1 where boardId = :boardId")
    int subtractLikeCount(@Param("boardId") Long boardId);


    @Query("select b.boardCount from BoardLike b where b.boardId = :boardId")
    int getBoardCount(@Param("boardId") Long boardId);

    //    @Query("UPDATE BoardLike b, LikeStatus s " +
//            "SET b.boardCount = b.boardCount - 1, s.status = true " +
//            "WHERE b.boardId = :boardId AND s.likeStatusId = :likeStatusId")

}
