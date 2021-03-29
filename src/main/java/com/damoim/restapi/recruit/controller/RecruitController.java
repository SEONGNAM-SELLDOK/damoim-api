package com.damoim.restapi.recruit.controller;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.recruit.model.*;
import com.damoim.restapi.recruit.service.RecruitService;
import com.damoim.restapi.reply.model.request.RequestSaveReply;
import com.damoim.restapi.reply.model.response.ResponseReply;
import com.damoim.restapi.reply.service.ReplyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@Api(value = "recruits", tags = "구인 관련 REST API")
@RequiredArgsConstructor
@RequestMapping(value = "recruits")
@RestController
public class RecruitController {
    private final RecruitService recruitService;
    private final ReplyService replyService;
    private static final String ROOT = "recruit";

    @PostMapping
    public ResponseEntity<RecruitsResponse> saveRecruit(@Valid @RequestBody RecruitSaveRequest saveRequest, @RequestParam(required = false) MultipartFile file) {
        return new ResponseEntity<>(new RecruitsResponse(recruitService.save(saveRequest, RequestFile.of(ROOT, file))), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RecruitsResponse> getRecruit(@Valid @PathVariable Long id) {
        return new ResponseEntity<>(new RecruitsResponse(recruitService.getById(id)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RecruitsResponse> updateRecruit(@Valid @RequestBody RecruitUpdateRequest updateRequest, @RequestParam(required = false) MultipartFile file, @AuthenticationPrincipal AuthUser authUser) {
        return new ResponseEntity<>(new RecruitsResponse(recruitService.update(updateRequest, RequestFile.of(ROOT, file), authUser)), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecruit(@Valid @PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) {
        recruitService.delete(id, authUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<RecruitsResponse> getRecruits(@PageableDefault(size = 6, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, RecruitGetRequest getRequest) {
        return new ResponseEntity<>(new RecruitsResponse(recruitService.getRecruitByCondition(pageable, getRequest)), HttpStatus.OK);
    }

    @PostMapping("/{no}/reply")
    public ResponseEntity<ResponseReply> saveReply(@PathVariable Long no,
                                                   @Valid @RequestBody RequestSaveReply requestSaveReply) {
        RequestSaveReply reply = requestSaveReply.checkUrl(ROOT);
        return ResponseEntity.ok(replyService.replySave(no, reply));
    }

    @GetMapping("/{no}/reply")
    public ResponseEntity<RecruitsWithReplyResponse> getReplyAndUsedItem(@PathVariable Long no) {
        return ResponseEntity.ok(new RecruitsWithReplyResponse(recruitService.getRecruitIncludeReply(no, BoardType.RECRUIT)));
    }
}
