package com.damoim.restapi.boards.dao;

import static com.damoim.restapi.boards.entity.QBoard.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.model.ListBoardsResponse;
import com.damoim.restapi.boards.model.QListBoardsResponse;
import com.damoim.restapi.boards.model.QReadBoardsResponse;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ReadBoardsResponse> findByBoardInfo(Long id, BoardType type) {
        return queryFactory.select(new QReadBoardsResponse(
            board.id,
            board.title,
            board.content,
            board.image,
            board.address,
            board.totalMember,
            board.currentMember,
            board.subject,
            board.endDate))
            .from(board)
            .where(
                board.id.eq(id),
                board.boardType.eq(type)
            )
            .fetch();
    }

    @Override
    public Page<ListBoardsResponse> searchBoard(BoardSearchCondition condition, Pageable pageable) {
        QueryResults<ListBoardsResponse> results = queryFactory
            .select(new QListBoardsResponse(
                board.title,
                board.endDate,
                board.address.country.as("boardsCountry"),
                board.address.city.as("boardsCity"),
                board.address.street.as("boardStreet"),
                board.totalMember,
                board.currentMember,
                board.subject,
                board.damoimTag.tag,
                board.image))
            .from(board)
                .where(
                        titleEq(condition.getTitle()),
                        boardsCountryEq(condition.getBoardsCountry()),
                        boardsCityEq(condition.getBoardsCity()),
                        boardStreetEq(condition.getBoardStreet()),
                        totalMemberEq(condition.getTotalMember()),
                        currentMemberEq(condition.getCurrentMember()),
                        subjectEq(condition.getSubject()),
                        damoimTagEq(condition.getDamoimTag()),
                        boardTypeEq(condition.getBoardType()),
                        fromTo(condition.getFrom(), condition.getTo())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ListBoardsResponse> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? board.title.eq(title) : null;
    }

    private BooleanExpression fromTo(LocalDate from, LocalDate to) {
        return (from != null || to != null) ? board.endDate.between(from.atStartOfDay(), to.atStartOfDay().plusDays(1L)) : null;
    }

    private BooleanExpression boardsCountryEq(String boardsCountry) {
        return StringUtils.hasText(boardsCountry) ? board.address.country.eq(boardsCountry) : null;
    }

    private BooleanExpression boardsCityEq(String boardsCity) {
        return StringUtils.hasText(boardsCity) ? board.address.city.eq(boardsCity) : null;
    }

    private BooleanExpression boardStreetEq(String boardStreet) {
        return StringUtils.hasText(boardStreet) ? board.address.street.eq(boardStreet) : null;
    }

    private BooleanExpression totalMemberEq(String totalMember) {
        return StringUtils.hasText(totalMember) ? board.totalMember.eq(totalMember) : null;
    }

    private BooleanExpression currentMemberEq(String currentMember) {
        return StringUtils.hasText(currentMember) ? board.currentMember.eq(currentMember) : null;
    }

    private BooleanExpression subjectEq(String subject) {
        return StringUtils.hasText(subject) ? board.subject.eq(subject) : null;
    }

    private BooleanExpression damoimTagEq(String damoimTag) {
        return StringUtils.hasText(damoimTag) ? board.damoimTag.tag.eq(damoimTag) : null;
    }

    private BooleanExpression boardTypeEq(BoardType type) {
        return type != null ? board.boardType.eq(type) : null;
    }
}
