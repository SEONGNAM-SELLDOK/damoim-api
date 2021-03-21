package com.damoim.restapi.like.dao;

import com.damoim.restapi.like.entity.LikeStatus;
import com.damoim.restapi.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author gisung.go
 * @since 2021-03-20
 */

@Repository
public interface LikeStatusRepository extends JpaRepository<LikeStatus, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE LikeStatus SET status = false where likeStatusId = :likeStatusId")
    int updateStatusFalse(@Param("likeStatusId") Long likeStatusId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE LikeStatus SET status = true where likeStatusId = :likeStatusId")
    int updateStatusTrue(@Param("likeStatusId") Long likeStatusId);

}
