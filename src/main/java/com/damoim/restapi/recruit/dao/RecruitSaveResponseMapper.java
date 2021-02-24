package com.damoim.restapi.recruit.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveResponse;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@Mapper(componentModel = "spring")
public interface RecruitSaveResponseMapper extends EntityMapper<RecruitSaveResponse, Recruit> {
}
