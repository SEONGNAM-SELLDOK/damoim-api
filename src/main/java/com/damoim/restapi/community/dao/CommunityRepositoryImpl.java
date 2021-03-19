package com.damoim.restapi.community.dao;

import static com.damoim.restapi.community.entity.QCommunity.community;

import com.damoim.restapi.community.model.ListCommunityResponse;
import com.damoim.restapi.community.model.QListCommunityResponse;
import com.damoim.restapi.community.model.QReadCommunityResponse;
import com.damoim.restapi.community.model.ReadCommunityResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gisung.go
 * @since 2021-03-18
 */

public class CommunityRepositoryImpl implements CommunityRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CommunityRepositoryImpl(JPAQueryFactory queryFactory) { this.queryFactory = queryFactory; }

    @Override
    public List<ReadCommunityResponse> findByCommunityInfo(Long id) {
        return queryFactory.select(new QReadCommunityResponse(
                community.id,
                community.title,
                community.content))
            .from(community)
            .where(community.id.eq(id))
            .fetch();
    }

    @Override
    public Page<ListCommunityResponse> searchCommunity(CommunitySearchCondition condition, Pageable pageable) {
        QueryResults<ListCommunityResponse> results = queryFactory
                .select(new QListCommunityResponse(
                        community.title,
                        community.content
                    ))
                .from(community)
                    .where(
                        titleEq(condition.getTitle())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ListCommunityResponse> nResults = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(nResults, pageable, total);
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? community.title.eq(title) : null;
    }
}
