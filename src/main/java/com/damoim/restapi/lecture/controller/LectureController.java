package com.damoim.restapi.lecture.controller;

import com.damoim.restapi.lecture.dao.LectureResponseMapper;
import com.damoim.restapi.lecture.model.LectureResponse;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.service.LectureService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Api(value = "lecture", description = "강의 추천 REST API")
@Slf4j
@RestController
@RequestMapping("lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    private final LectureResponseMapper lectureResponseMapper;

    @PostMapping
    public ResponseEntity<LectureResponse> save(@Valid @RequestBody LectureSaveRequest lectureSaveRequest, MultipartFile file) {
        return new ResponseEntity<>(lectureResponseMapper.toDto(lectureService.save(lectureSaveRequest, file)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LectureResponse>> retrieve() {
        return new ResponseEntity<>(lectureResponseMapper.toGetDtos(lectureService.list()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureResponse> selectItem(@PathVariable("id") Long id) {
        return new ResponseEntity<>(lectureResponseMapper.toDto(lectureService.findById(id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.info("Delete Lectures Request - {}", id);
        lectureService.delete(id);
        return status(HttpStatus.NO_CONTENT).build();
    }

}
