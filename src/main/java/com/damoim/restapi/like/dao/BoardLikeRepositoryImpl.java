package com.damoim.restapi.like.dao;

import com.damoim.restapi.boards.entity.BoardType;
import static com.damoim.restapi.like.entity.QBoardLike.boardLike;
import static com.damoim.restapi.like.entity.QLikeStatus.likeStatus;

import com.damoim.restapi.like.model.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

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
                boardLike.id,
                likeStatus.boardLike.id,
                boardLike.boardId,
                likeStatus.status,
                boardLike.likeCount))
            .from(boardLike)
            .leftJoin(boardLike.likeStatus, likeStatus)
            .where(
                boardLike.boardId.eq(boardId),
                boardLike.boardType.eq(type)
            )
            .fetch();
    }

    @Override
    public Page<ListLikeResponse> searchBoardLike(BoardLikeSearchCondition condition, Pageable pageable) {
        QueryResults<ListLikeResponse> results = queryFactory
            .select(new QListLikeResponse(
                boardLike.boardId,
                likeStatus.status,
                boardLike.likeCount))
            .from(boardLike)
            .leftJoin(boardLike.likeStatus, likeStatus)
            .where(statusEq(condition.getStatus()))
            .orderBy(popularity(condition.getPopular()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<ListLikeResponse> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression statusEq(Boolean status) {
        return Objects.nonNull(status) ? likeStatus.status.eq(status) : null;
    }

    private OrderSpecifier popularity(String popular) {
        if(Objects.isNull(popular) || Objects.isNull(popular.equals(popular))) {
            return boardLike.boardId.desc();
        }
        return boardLike.likeCount.desc();
    }
}
