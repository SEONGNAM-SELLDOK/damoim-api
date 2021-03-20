package com.damoim.restapi.reply.dao;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.reply.entity.Reply;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select distinct r from Reply r left join fetch r.childReply"
        + " where r.boardType=:boardType and r.boardId=:boardId")
    List<Reply> parentList(BoardType boardType, Long boardId);

    @Query("select r from Reply r left join fetch r.childReply where r.no=:id")
    Optional<Reply> findByIdIncludeChildList(Long id);
}