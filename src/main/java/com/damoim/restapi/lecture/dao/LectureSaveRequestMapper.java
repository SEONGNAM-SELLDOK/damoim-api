package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/03/10
 */
@Mapper(componentModel = "spring")
public interface LectureSaveRequestMapper extends EntityMapper<LectureSaveRequest, Lecture> {
}
