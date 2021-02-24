package com.damoim.restapi.recruit.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@Mapper(componentModel = "spring")
public interface RecruitSaveRequestMapper extends EntityMapper<RecruitSaveRequest, Recruit> {
}
