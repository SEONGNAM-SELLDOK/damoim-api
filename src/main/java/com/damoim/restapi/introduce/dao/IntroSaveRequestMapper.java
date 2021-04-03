package com.damoim.restapi.introduce.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.introduce.entity.Introduce;
import com.damoim.restapi.introduce.model.IntroSaveRequest;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@Mapper(componentModel = "spring")
public interface IntroSaveRequestMapper extends EntityMapper<IntroSaveRequest, Introduce> {
}
