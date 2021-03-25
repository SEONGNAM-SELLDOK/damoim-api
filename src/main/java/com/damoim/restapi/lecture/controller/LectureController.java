package com.damoim.restapi.lecture.controller;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.lecture.dao.LectureResponseWithReply;
import com.damoim.restapi.lecture.model.LectureGetRequest;
import com.damoim.restapi.lecture.model.LectureResponse;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import com.damoim.restapi.lecture.service.LectureService;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.reply.model.request.RequestSaveReply;
import com.damoim.restapi.reply.model.response.ResponseReply;
import com.damoim.restapi.reply.service.ReplyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final ReplyService replyService;
    private static final String ROOT = "lecture";

    @PostMapping
    public ResponseEntity<LectureResponse> save(@Valid @RequestBody LectureSaveRequest lectureSaveRequest, MultipartFile file) {
        return new ResponseEntity<>(lectureService.save(lectureSaveRequest, RequestFile.of(ROOT, file)), HttpStatus.CREATED);
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
    public ResponseEntity<LectureResponse> update(@Valid @RequestBody LectureUpdateRequest lectureUpdateRequest, MultipartFile file, @AuthenticationPrincipal AuthUser authUser) {
        return new ResponseEntity<>(lectureService.update(lectureUpdateRequest, RequestFile.of(ROOT, file), authUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("Delete Lectures Request - {}", id);
        lectureService.delete(id, authUser);
        return status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{no}/reply")
    public ResponseEntity<ResponseReply> saveReply(@PathVariable Long no,
                                                   @Valid @RequestBody RequestSaveReply requestSaveReply) {
        RequestSaveReply reply = requestSaveReply.checkUrl(ROOT);
        return ResponseEntity.ok(replyService.replySave(no, reply));
    }

    @GetMapping("/{no}/reply")
    public ResponseEntity<LectureResponseWithReply> getReplyAndUsedItem(@PathVariable Long no) {
        return ResponseEntity.ok(lectureService.getLectureIncludeReply(no, BoardType.LECTURE));
    }

}
