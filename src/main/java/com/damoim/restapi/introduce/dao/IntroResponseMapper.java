package com.damoim.restapi.introduce.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.introduce.entity.Introduce;
import com.damoim.restapi.introduce.model.IntroResponse;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@Mapper(componentModel = "spring")
public interface IntroResponseMapper extends EntityMapper<IntroResponse, Introduce> {
}
