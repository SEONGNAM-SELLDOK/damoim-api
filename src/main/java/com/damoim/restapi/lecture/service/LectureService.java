package com.damoim.restapi.lecture.service;

import com.damoim.restapi.lecture.dao.LectureRepository;
import com.damoim.restapi.lecture.dao.LectureSaveRequestMapper;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
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
    private final LectureSaveRequestMapper saveRequestMapper;

    public Lecture save(LectureSaveRequest request) {
        Lecture lecture = saveRequestMapper.toEntity(request);
        return lectureRepository.save(lecture);
    }

    @Transactional(readOnly = true)
    public List<Lecture> list() {
        return lectureRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Lecture findById(Long id) {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Lecture not found (id = {})", id)));
    }

    public void delete(Long id) {
        lectureRepository.deleteById(id);
    }
}
