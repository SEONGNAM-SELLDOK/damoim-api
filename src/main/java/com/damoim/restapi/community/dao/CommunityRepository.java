package com.damoim.restapi.community.dao;

import com.damoim.restapi.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gisung go
 * @since 2021-03-18
 * */

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {
}
