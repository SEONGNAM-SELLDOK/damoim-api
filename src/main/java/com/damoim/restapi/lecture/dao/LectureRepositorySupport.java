package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.entity.LectureSubject;
import com.damoim.restapi.lecture.model.LectureGetRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.damoim.restapi.lecture.entity.QLecture.lecture;

/**
 * @author SeongRok.Oh
 * @since 2021/03/12
 */
@Repository
public class LectureRepositorySupport extends QuerydslRepositorySupport {
    private JPAQueryFactory queryFactory;

    public LectureRepositorySupport(JPAQueryFactory queryFactory) {
        super(Lecture.class);
        this.queryFactory = queryFactory;
    }

    /*
     * 제목 - eq
     * 상세내용 - like
     * 발표자 - like
     * 주제 - eq
     * 경로 - like
     * 마감일 - in
     * 등록자 - like
     */
    public Set<Lecture> search(LectureGetRequest getRequest, Pageable pageable) {
        if (Objects.isNull(getRequest)) {
            return new HashSet<>(queryFactory.selectFrom(lecture)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch());
        }
        return new HashSet<>(queryFactory.selectFrom(lecture)
                .where(
                        eqSth(lecture.title, getRequest.getTitle()),
                        likeSth(lecture.description, getRequest.getDescription()),
                        likeSth(lecture.speaker, getRequest.getSpeaker()),
                        eqEnumSth(lecture.subject, getRequest.getSubject()),
                        likeSth(lecture.route, getRequest.getRoute()),
                        likeSth(lecture.register,getRequest.getRegister())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
        );
    }

    private BooleanExpression eqEnumSth(EnumPath<LectureSubject> subject, LectureSubject compareSub) {
        if (Objects.isNull(subject) || Objects.isNull(compareSub)) {
            return null;
        }
        return subject.eq(compareSub);
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
