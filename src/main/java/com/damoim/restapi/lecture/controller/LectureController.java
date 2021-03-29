package com.damoim.restapi.lecture.controller;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.lecture.model.*;
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
    public ResponseEntity<LecturesResponse> save(@Valid @RequestBody LectureSaveRequest lectureSaveRequest, MultipartFile file) {
        return new ResponseEntity<>(new LecturesResponse(lectureService.save(lectureSaveRequest, RequestFile.of(ROOT, file))), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<LecturesResponse> retrieve(@PageableDefault(size = 6, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable, LectureGetRequest getRequest) {
        return new ResponseEntity<>(new LecturesResponse(lectureService.getLectureByCondition(pageable, getRequest)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturesResponse> selectItem(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new LecturesResponse(lectureService.findById(id)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<LecturesResponse> update(@Valid @RequestBody LectureUpdateRequest lectureUpdateRequest, MultipartFile file, @AuthenticationPrincipal AuthUser authUser) {
        return new ResponseEntity<>(new LecturesResponse(lectureService.update(lectureUpdateRequest, RequestFile.of(ROOT, file), authUser)), HttpStatus.OK);
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
    public ResponseEntity<LecturesWithReplyResponse> getReplyAndUsedItem(@PathVariable Long no) {
        return ResponseEntity.ok(new LecturesWithReplyResponse(lectureService.getLectureIncludeReply(no, BoardType.LECTURE)));
    }

}
