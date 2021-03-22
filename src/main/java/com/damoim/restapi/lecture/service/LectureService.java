package com.damoim.restapi.lecture.service;

import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.lecture.dao.*;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.LectureGetRequest;
import com.damoim.restapi.lecture.model.LectureResponse;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final LectureResponseMapper responseMapper;

    private static final String ROOT = "lecture";

    public LectureResponse save(@Valid LectureSaveRequest request, MultipartFile file) {
        String fileName = null;
        if (Objects.nonNull(file)) {
            fileName = fileUtil.upload(RequestFile.of(ROOT, file));
        }
        Lecture lecture = saveRequestMapper.toEntity(request);
        lecture.setImage(fileName);
        return responseMapper.toDto(lectureRepository.save(lecture));
    }

    @Transactional(readOnly = true)
    public LectureResponse findById(long id) {
        return responseMapper.toDto(lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Lecture not found (id = %s)", id))));
    }

    public void delete(long id) {
        lectureRepository.deleteById(id);
    }

    public LectureResponse update(@Valid LectureUpdateRequest request, MultipartFile file) {
        String fileName = null;
        if (Objects.nonNull(file)) {
            fileName = fileUtil.upload(RequestFile.of(ROOT, file));
        }
        Lecture lecture = updateRequestMapper.toEntity(request);
        lecture.setImage(fileName);
        return responseMapper.toDto(lectureRepository.save(lecture));
    }

    public Set<LectureResponse> getLectureByCondition(Pageable pageable, LectureGetRequest getRequest) {
        return lectureResponseSet(repositorySupport.search(getRequest, pageable));
    }

    private Set<LectureResponse> lectureResponseSet(Set<Lecture> lectureSet){
        return lectureSet.stream().map(responseMapper::toDto).collect(Collectors.toSet());
    }
}
