package com.damoim.restapi.lecture.controller;

import com.damoim.restapi.lecture.dao.LectureMapper;
import com.damoim.restapi.lecture.model.GetLectureResponse;
import com.damoim.restapi.lecture.model.SaveLectureRequest;
import com.damoim.restapi.lecture.service.LectureService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final LectureMapper lectureMapper;

    @PostMapping
    public ResponseEntity<GetLectureResponse> save(@Valid @RequestBody SaveLectureRequest saveLectureRequest) {
        return new ResponseEntity<>(lectureMapper.toDto(lectureService.save(saveLectureRequest)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetLectureResponse>> retrieve() {
        return new ResponseEntity<>(lectureMapper.toGetDtos(lectureService.list()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetLectureResponse> selectItem(@PathVariable("id") Long id) {
        return new ResponseEntity<>(lectureMapper.toDto(lectureService.findById(id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.info("Delete Lectures Request - {}", id);
        lectureService.delete(id);
        return status(HttpStatus.NO_CONTENT).build();
    }

}
