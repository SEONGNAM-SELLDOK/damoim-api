package com.damoim.restapi.recruit.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitUpdateRequest;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/02/24
 */
@Mapper(componentModel = "spring")
public interface RecruitUpdateRequestMapper extends EntityMapper<RecruitUpdateRequest, Recruit> {
}
