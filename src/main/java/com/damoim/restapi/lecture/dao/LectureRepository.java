package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
