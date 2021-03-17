package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import org.mapstruct.Mapper;

/**
 * @author SeongRok.Oh
 * @since 2021/03/10
 */
@Mapper(componentModel = "spring")
public interface LectureUpdateRequestMapper extends EntityMapper<LectureUpdateRequest, Lecture> {
}
