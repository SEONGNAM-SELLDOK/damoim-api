package com.damoim.restapi.lecture.service;

import com.damoim.restapi.lecture.dao.LectureRepository;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.SaveLectureRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .image(request.getImage())
                .register(request.getRegister())
                .build();

                return lectureRepository.save(lecture);
    }

    public List<Lecture> list() {
        return lectureRepository.findAll();
    }

    public Lecture findById(Long id) {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Lecture not found (id = {})", id)));
    }

    @Transactional
    public void delete(Long id) {
        lectureRepository.deleteById(id);
    }
}
