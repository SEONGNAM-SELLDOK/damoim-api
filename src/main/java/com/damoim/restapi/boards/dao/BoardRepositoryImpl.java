package com.damoim.restapi.boards.dao;

import static com.damoim.restapi.boards.entity.QBoard.board;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.model.ListBoardsResponse;
import com.damoim.restapi.boards.model.QListBoardsResponse;
import com.damoim.restapi.boards.model.QReadBoardsResponse;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
        return Strings.isBlank(title) ? null : board.title.eq(title);
    }

    private BooleanExpression fromTo(LocalDate from, LocalDate to) {
        if (from == null && to == null) {
            return null;
        }

        if(Objects.nonNull(to) && Objects.isNull(from)) {
            return board.endDate.after(to.atStartOfDay().plusDays(1L));
        }

        if(Objects.isNull(to)) {
            return board.endDate.before(from.atStartOfDay());
        }
        return board.endDate.between(from.atStartOfDay(), to.atStartOfDay().plusDays(1L));
    }

    private BooleanExpression boardsCountryEq(String boardsCountry) {
        return Strings.isBlank(boardsCountry) ? null : board.address.country.eq(boardsCountry);
    }

    private BooleanExpression boardsCityEq(String boardsCity) {
        return Strings.isBlank(boardsCity) ? null : board.address.city.eq(boardsCity);
    }

    private BooleanExpression boardStreetEq(String boardStreet) {
        return Strings.isBlank(boardStreet) ? null : board.address.street.eq(boardStreet);
    }

    private BooleanExpression totalMemberEq(Integer totalMember) {
        return totalMember == null ? null : board.totalMember.eq(totalMember);
    }

    private BooleanExpression currentMemberEq(Integer currentMember) {
        return currentMember == null ? null : board.currentMember.eq(currentMember);
    }

    private BooleanExpression subjectEq(String subject) {
        return Strings.isBlank(subject) ? null : board.subject.eq(subject);
    }

    private BooleanExpression damoimTagEq(String damoimTag) {
        return Strings.isBlank(damoimTag) ? null : board.damoimTag.tag.eq(damoimTag);
    }

    private BooleanExpression boardTypeEq(BoardType type) {
        return Objects.nonNull(type) ? board.boardType.eq(type) : null;
    }
}
