package com.damoim.restapi.community.dao;

import com.damoim.restapi.community.model.ListCommunityResponse;
import com.damoim.restapi.community.model.ReadCommunityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author gisung go
 * @since 2021-03-18
 * */

public interface CommunityRepositoryCustom {
    List<ReadCommunityResponse> findByCommunityInfo(Long id);
    Page<ListCommunityResponse> searchCommunity(CommunitySearchCondition condition
    , Pageable pageable);
}
