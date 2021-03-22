package com.damoim.restapi.like.dao;

import com.damoim.restapi.like.entity.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author gisung.go
 * @since 2021-03-20
 */

public interface LikeStatusRepository extends JpaRepository<LikeStatus, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE LikeStatus SET status=:status WHERE (status = false) AND (board_like_id = :boardLikeId)")
    int updateStatusTrue(@Param("status") Boolean status, @Param("boardLikeId") Long boardLikeId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE LikeStatus SET status=:status WHERE (status = true) AND (board_like_id = :boardLikeId)")
    int updateStatusFalse(@Param("status") Boolean status, @Param("boardLikeId") Long boardLikeId);
}
