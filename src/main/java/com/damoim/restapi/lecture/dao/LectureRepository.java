package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.lecture.entity.Lecture;
import org.springframework.data.repository.CrudRepository;

public interface LectureRepository extends CrudRepository<Lecture, Long> {
}
