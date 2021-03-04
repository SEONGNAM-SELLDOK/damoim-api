package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.GetLectureResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * LectureMapper Class
 * @author leekyunghee
 * @since 2021. 02. 25
 */

@Mapper(componentModel = "spring")
public interface LectureMapper extends EntityMapper<GetLectureResponse, Lecture> {
    List<GetLectureResponse> toGetDtos(List<Lecture> lectures);

    Lecture toGetDto(Lecture id);
}
