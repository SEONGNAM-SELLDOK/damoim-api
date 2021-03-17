package com.damoim.restapi.lecture.service;

import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.lecture.dao.LectureRepository;
import com.damoim.restapi.lecture.dao.LectureRepositorySupport;
import com.damoim.restapi.lecture.dao.LectureSaveRequestMapper;
import com.damoim.restapi.lecture.dao.LectureUpdateRequestMapper;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.LectureGetRequest;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author leekyunghee
 * @since 2021. 02. 25
 */

@Validated
@Transactional
@RequiredArgsConstructor
@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LectureSaveRequestMapper saveRequestMapper;
    private final LectureUpdateRequestMapper updateRequestMapper;
    private final DamoimFileUtil fileUtil;
    private final LectureRepositorySupport repositorySupport;

    private static final String ROOT = "lecture";

    public Lecture save(@Valid LectureSaveRequest request, MultipartFile file) {
        String fileName = null;
        if (Objects.nonNull(file)) {
            fileName = fileUtil.upload(RequestFile.of(ROOT, file));
        }
        Lecture lecture = saveRequestMapper.toEntity(request);
        lecture.setImage(fileName);
        return lectureRepository.save(lecture);
    }

    @Transactional(readOnly = true)
    public List<Lecture> list() {
        return lectureRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Lecture findById(long id) {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Lecture not found (id = %s)", id)));
    }

    public void delete(long id) {
        lectureRepository.deleteById(id);
    }

    public Lecture update(@Valid LectureUpdateRequest request, MultipartFile file) {
        String fileName = null;
        if (Objects.nonNull(file)) {
            fileName = fileUtil.upload(RequestFile.of(ROOT, file));
        }
        Lecture lecture = updateRequestMapper.toEntity(request);
        lecture.setImage(fileName);
        return lectureRepository.save(lecture);
    }

    public Set<Lecture> getLectureByCondition(Pageable pageable, LectureGetRequest getRequest) {
        return repositorySupport.search(getRequest, pageable);
    }
}
