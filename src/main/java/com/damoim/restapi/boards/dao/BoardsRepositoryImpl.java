package com.damoim.restapi.boards.dao;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.entity.QBoards;
import com.damoim.restapi.boards.model.ListBoardsResponse;
import com.damoim.restapi.boards.model.QListBoardsResponse;
import com.damoim.restapi.boards.model.QReadBoardsResponse;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import static com.damoim.restapi.boards.entity.QBoards.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
public class BoardsRepositoryImpl implements BoardsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardsRepositoryImpl(JPAQueryFactory queryFactory) { this.queryFactory = queryFactory; }

    @Override
    public List<ReadBoardsResponse> findByBoardInfo(Long id, BoardType type) {
        return queryFactory.select(new QReadBoardsResponse(
                boards.id,
                boards.title,
                boards.content,
                boards.image,
                boards.address,
                boards.totalMember,
                boards.currentMember,
                boards.subject,
                boards.endDate))
            .from(boards)
            .where(
                boards.id.eq(id),
                boards.boardType.eq(type)
            )
            .fetch();
    }

    @Override
    public Page<ListBoardsResponse> searchBoard(BoardsSearchCondition condition, Pageable pageable) {
        QueryResults<ListBoardsResponse> results = queryFactory
                .select(new QListBoardsResponse(
                        boards.title,
                        boards.endDate,
                        boards.address.country.as("boardsCountry"),
                        boards.address.city.as("boardsCity"),
                        boards.address.street.as("boardStreet"),
                        boards.totalMember,
                        boards.currentMember,
                        boards.subject,
                        boards.damoimTag.tag,
                        boards.image))
                .from(boards)
                .where(
                        titleEq(condition.getTitle()),
                        endDateEq(condition.getEndDate()),
                        boardsCountryEq(condition.getBoardsCountry()),
                        boardsCityEq(condition.getBoardsCity()),
                        boardStreetEq(condition.getBoardStreet()),
                        totalMemberEq(condition.getTotalMember()),
                        currentMemberEq(condition.getCurrentMember()),
                        subjectEq(condition.getSubject()),
                        damoimTagEq(condition.getDamoimTag()),
                        boardTypeEq(condition.getBoardType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ListBoardsResponse> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? boards.title.eq(title) : null;
    }

    private BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate != null ? boards.endDate.eq(endDate) : null;
    }

    private BooleanExpression boardsCountryEq(String boardsCountry) {
        return StringUtils.hasText(boardsCountry) ? boards.address.country.eq(boardsCountry) : null;
    }

    private BooleanExpression boardsCityEq(String boardsCity) {
        return StringUtils.hasText(boardsCity) ? boards.address.city.eq(boardsCity) : null;
    }

    private BooleanExpression boardStreetEq(String boardStreet) {
        return StringUtils.hasText(boardStreet) ? boards.address.street.eq(boardStreet) : null;
    }

    private BooleanExpression totalMemberEq(String totalMember) {
        return StringUtils.hasText(totalMember) ? boards.totalMember.eq(totalMember) : null;
    }

    private BooleanExpression currentMemberEq(String currentMember) {
        return StringUtils.hasText(currentMember) ? boards.currentMember.eq(currentMember) : null;
    }

    private BooleanExpression subjectEq(String subject) {
        return StringUtils.hasText(subject) ? boards.subject.eq(subject) : null;
    }

    private BooleanExpression damoimTagEq(String damoimTag) {
        return StringUtils.hasText(damoimTag) ? boards.damoimTag.tag.eq(damoimTag) : null;
    }

    private BooleanExpression boardTypeEq(BoardType type) {
        return type != null ? boards.boardType.eq(type) : null;
    }
}
