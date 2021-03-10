package com.damoim.restapi.lecture.service;

import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.lecture.dao.LectureRepository;
import com.damoim.restapi.lecture.dao.LectureSaveRequestMapper;
import com.damoim.restapi.lecture.dao.LectureUpdateRequestMapper;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final LectureUpdateRequestMapper updateRequestMapper;
    private final DamoimFileUtil fileUtil;

    private static final String ROOT = "lecture";

    public Lecture save(LectureSaveRequest request, MultipartFile file) {
        String fileName = fileUtil.upload(RequestFile.of(ROOT, file));
        Lecture lecture = saveRequestMapper.toEntity(request);
        lecture.setImage(fileName);
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

    public Lecture update(LectureUpdateRequest request, MultipartFile file) {
        String fileName = fileUtil.upload(RequestFile.of(ROOT, file));
        Lecture lecture = updateRequestMapper.toEntity(request);
        lecture.setImage(fileName);
        return lecture;
    }
}
