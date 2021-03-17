package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.LectureResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * LectureMapper Class
 * @author leekyunghee
 * @since 2021. 02. 25
 */

@Mapper(componentModel = "spring")
public interface LectureResponseMapper extends EntityMapper<LectureResponse, Lecture> {
    List<LectureResponse> toGetDtos(List<Lecture> lectures);

    Lecture toGetDto(Lecture id);
}
