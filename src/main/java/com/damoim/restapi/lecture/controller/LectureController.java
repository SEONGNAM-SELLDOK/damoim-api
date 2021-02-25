package com.damoim.restapi.lecture.controller;

import com.damoim.restapi.lecture.dao.LectureMapper;
import com.damoim.restapi.lecture.model.SaveLectureRequest;
import com.damoim.restapi.lecture.model.SaveLectureResponse;
import com.damoim.restapi.lecture.service.LectureService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "lecture", description = "강의 추천 저장 API")
@RestController
@RequestMapping("lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;
    private final LectureMapper lectureMapper;

    @PostMapping
    public ResponseEntity<SaveLectureResponse> save(@Valid @RequestBody SaveLectureRequest saveLectureRequest) {
        return new ResponseEntity<>(lectureMapper.toDto(lectureService.save(saveLectureRequest)), HttpStatus.CREATED);
    }
}
