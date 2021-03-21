package com.damoim.restapi.like.dao;

import com.damoim.restapi.boards.entity.BoardType;
import static com.damoim.restapi.like.entity.QBoardLike.boardLike;
import static com.damoim.restapi.like.entity.QLikeStatus.likeStatus;

import com.damoim.restapi.like.model.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

/**
 * @author gisung.go
 * @since 2021-03-20
 */

public class BoardLikeRepositoryImpl implements BoardLikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardLikeRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ReadLikeResponse> findByLikeInfo(Long boardId, BoardType type) {
        return queryFactory.select(new QReadLikeResponse(
                boardLike.boardId,
                likeStatus.status,
                boardLike.boardCount))
            .from(boardLike)
            .leftJoin(boardLike.likeStatus, likeStatus)
            .where(
                boardLike.boardId.eq(boardId),
                boardLike.boardType.eq(type)
            )
            .fetch();
    }

//    public List<ReadLikeResponse> addLikeCount(Long boardId, Long likeStatusId) {
//
//        return queryFactory.update(boardLike)
//                .set(
//                        boardLike.boardCount.add(1),
//                        likeStatus.status.isFalse()
//                ).where()
//                .execute();
//    }

//    @Override
//    public List<ReadLikeResponse> changeLike(ChangeLikeRequest request) {
//
//        return new List<ReadLikeResponse>;
//    }
}
