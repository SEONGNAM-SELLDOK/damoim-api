package com.damoim.restapi.recruit.dao;

import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitGetRequest;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.damoim.restapi.recruit.entity.QRecruit.recruit;

/**
 * @author SeongRok.Oh
 * @since 2021/03/03
 */
@Repository
public class RecruitRepositorySupport extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;

    public RecruitRepositorySupport(JPAQueryFactory queryFactory) {
        super(Recruit.class);
        this.queryFactory = queryFactory;
    }

    /*
     * 제목 - eq
     * 상세내용 - like
     * 회사 - like
     * 근무지 - eq
     * 채용 보상금 - 조회금액보다 작거나 같음
     * 태그 - eq
     * 등록자 - like
     */
    public Set<Recruit> search(RecruitGetRequest request, Pageable pageable) {
        if (Objects.isNull(request)) {
            return new HashSet<>(queryFactory.selectFrom(recruit)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch());
        }
        return new HashSet<>(queryFactory.selectFrom(recruit)
                .where(
                        eqSth(recruit.title, request.getTitle()),
                        likeSth(recruit.description, request.getDescription()),
                        likeSth(recruit.company, request.getCompany()),
                        eqSth(recruit.location, request.getLocation()),
                        gtSth(recruit.reward, request.getReward()),
                        eqSthArray(recruit.tags, request.getTags()),
                        likeSth(recruit.register, request.getRegister()),
                        afterSth(recruit.createdDate, request.getFrom()),
                        beforeSth(recruit.createdDate, request.getTo())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch())
                ;
    }

    private BooleanExpression gtSth(NumberPath<Integer> compare1, Integer compare2) {
        if (Objects.isNull(compare1) || Objects.isNull(compare2)) {
            return null;
        }
        return compare1.gt(compare2);
    }

    private BooleanExpression eqSthArray(ArrayPath<String[], String> array, String[] compare) {
        if (Objects.isNull(array) || Objects.isNull(compare)) {
            return null;
        }
        return array.in(compare);
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

    private BooleanExpression beforeSth(DateTimePath<LocalDateTime> time, LocalDate compare) {
        if (Objects.isNull(time) || Objects.isNull(compare)) {
            return null;
        }
        return time.before(LocalDateTime.of(compare, LocalTime.MIDNIGHT));
    }

    private BooleanExpression afterSth(DateTimePath<LocalDateTime> time, LocalDate compare) {
        if (Objects.isNull(time) || Objects.isNull(compare)) {
            return null;
        }
        return time.after(LocalDateTime.of(compare, LocalTime.MIDNIGHT));
    }

}
