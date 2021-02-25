package com.damoim.restapi.lecture.service;

import com.damoim.restapi.lecture.dao.LectureRepository;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.SaveLectureRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author leekyunghee
 * @since 2021. 02. 25
 */

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public Lecture save(SaveLectureRequest request) {
        Lecture lecture = Lecture.builder()
                .lectureId(request.getLectureId())
                .title(request.getTitle())
                .description(request.getDescription())
                .register(request.getRegister())
                .build();

                return lectureRepository.save(lecture);
    }
}
