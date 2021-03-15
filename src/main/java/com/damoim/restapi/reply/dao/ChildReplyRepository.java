package com.damoim.restapi.reply.dao;

import com.damoim.restapi.reply.entity.ChildReply;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
public interface ChildReplyRepository extends JpaRepository<ChildReply, Long> {

    @Query("select r from ChildReply r join fetch r.parentReply t join fetch t.childReply"
        + " where r.no=:id")
    Optional<ChildReply> getChildReplyIncludeParentReply(Long id);
}
