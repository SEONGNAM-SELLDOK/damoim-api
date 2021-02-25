package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.SaveLectureResponse;
import org.mapstruct.Mapper;

/**
 * LecterMapper Class
 * @author leekyunghee
 * @since 2021. 02. 25
 */

@Mapper(componentModel = "spring")
public interface LectureMapper extends EntityMapper<SaveLectureResponse, Lecture> {
}
