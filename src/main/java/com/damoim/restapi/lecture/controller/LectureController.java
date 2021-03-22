package com.damoim.restapi.lecture.controller;

import com.damoim.restapi.lecture.model.LectureGetRequest;
import com.damoim.restapi.lecture.model.LectureResponse;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import com.damoim.restapi.lecture.service.LectureService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.http.ResponseEntity.status;

@Api(value = "lecture")
@Slf4j
@RestController
@RequestMapping("lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<LectureResponse> save(@Valid @RequestBody LectureSaveRequest lectureSaveRequest, MultipartFile file) {
        return new ResponseEntity<>(lectureService.save(lectureSaveRequest, file), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<LectureResponse>> retrieve(@PageableDefault(size = 6, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable, LectureGetRequest getRequest) {
        return new ResponseEntity<>(lectureService.getLectureByCondition(pageable, getRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureResponse> selectItem(@PathVariable("id") Long id) {
        return new ResponseEntity<>(lectureService.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<LectureResponse> update(@Valid @RequestBody LectureUpdateRequest lectureUpdateRequest, MultipartFile file) {
        return new ResponseEntity<>(lectureService.update(lectureUpdateRequest, file), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.info("Delete Lectures Request - {}", id);
        lectureService.delete(id);
        return status(HttpStatus.NO_CONTENT).build();
    }

}
