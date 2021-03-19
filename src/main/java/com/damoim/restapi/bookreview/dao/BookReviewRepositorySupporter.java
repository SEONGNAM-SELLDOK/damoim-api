package com.damoim.restapi.bookreview.dao;

import com.damoim.restapi.bookreview.entity.BookReview;
import com.damoim.restapi.bookreview.model.BookReviewGetRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.SetPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.damoim.restapi.bookreview.entity.QBookReview.bookReview;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@Repository
public class BookReviewRepositorySupporter extends QuerydslRepositorySupport {
    private JPAQueryFactory jpaQueryFactory;

    public BookReviewRepositorySupporter(JPAQueryFactory jpaQueryFactory) {
        super(BookReview.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Set<BookReview> search(BookReviewGetRequest getRequest, Pageable pageable) {
        if (Objects.isNull(getRequest)) {
            return new HashSet<>(jpaQueryFactory.selectFrom(bookReview)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch());
        }
        return new HashSet<>(jpaQueryFactory.selectFrom(bookReview)
                .where(
                        eqSth(bookReview.title, getRequest.getTitle()),
                        likeSth(bookReview.description, getRequest.getDescription()),
                        eqSth(bookReview.isbn, getRequest.getIsbn()),
                        eqSth(bookReview.publisher, getRequest.getPublisher()),
                        likeSth(bookReview.writer, getRequest.getWriter()),
                        eqSth(bookReview.subject, getRequest.getSubject()),
                        containSth(bookReview.tag, getRequest.getTag()),
                        fromSth(bookReview.deadline, getRequest.getDeadlineFrom()),
                        toSth(bookReview.deadline, getRequest.getDeadlineTo()),
                        likeSth(bookReview.register, getRequest.getRegister())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch());
    }

    private BooleanExpression fromSth(DatePath<LocalDate> compare, LocalDate from) {
        if (Objects.isNull(compare) || Objects.isNull(from)) {
            return null;
        }
        return compare.after(from);
    }

    private BooleanExpression toSth(DatePath<LocalDate> compare, LocalDate to) {
        if (Objects.isNull(compare) || Objects.isNull(to)) {
            return null;
        }
        return compare.before(to);
    }

    private BooleanExpression containSth(SetPath<String, StringPath> collections, String compare) {
        if (Objects.isNull(collections) || Objects.isNull(compare)) {
            return null;
        }
        return collections.contains(compare);
    }

    private BooleanExpression eqSth(StringPath compare1, String compare2) {
        if (Objects.isNull(compare1) || Objects.isNull(compare2)) {
            return null;
        }
        return compare1.eq(compare2);
    }

    private BooleanExpression likeSth(StringPath compare1, String compare2) {
        if (Objects.isNull(compare1) || Objects.isNull(compare2)) {
            return null;
        }
        return compare1.contains(compare2);
    }

}
