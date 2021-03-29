package com.damoim.restapi.bookreview.controller;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.bookreview.model.*;
import com.damoim.restapi.bookreview.service.BookReviewService;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.member.model.AuthUser;
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
 * @since 2021/03/16
 */
@Api(value = "bookreviews")
@RequiredArgsConstructor
@RequestMapping("/bookreviews")
@RestController
public class BookReviewController {
    private final BookReviewService bookReviewService;
    private final ReplyService replyService;
    private static final String ROOT = "bookreview";

    @PostMapping
    public ResponseEntity<BookReviewsResponse> create(@Valid @RequestBody BookReviewSaveRequest reviewSaveRequest, MultipartFile multipartFile) {
        return new ResponseEntity<>(new BookReviewsResponse(bookReviewService.save(reviewSaveRequest, RequestFile.of(ROOT, multipartFile))), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookReviewsResponse> update(@Valid @RequestBody BookReviewUpdateRequest reviewUpdateRequest, MultipartFile multipartFile, @AuthenticationPrincipal AuthUser authUser) {
        return new ResponseEntity<>(new BookReviewsResponse(bookReviewService.update(reviewUpdateRequest, RequestFile.of(ROOT, multipartFile), authUser)), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookReviewsResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(new BookReviewsResponse(bookReviewService.getById(id)), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) {
        bookReviewService.delete(id, authUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<BookReviewsResponse> getByCondition(@PageableDefault(size = 6, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable, BookReviewGetRequest getRequest) {
        return new ResponseEntity<>(new BookReviewsResponse(bookReviewService.getByCondition(getRequest, pageable)), HttpStatus.OK);
    }

    @PostMapping("/{no}/reply")
    public ResponseEntity<ResponseReply> saveReply(@PathVariable Long no,
                                                   @Valid @RequestBody RequestSaveReply requestSaveReply) {
        RequestSaveReply reply = requestSaveReply.checkUrl(ROOT);
        return ResponseEntity.ok(replyService.replySave(no, reply));
    }

    @GetMapping("/{no}/reply")
    public ResponseEntity<BookReviewsWithReplyResponse> getReplyAndUsedItem(@PathVariable Long no) {
        return ResponseEntity.ok(new BookReviewsWithReplyResponse(bookReviewService.getBookReviewIncludeReply(no, BoardType.BOOKREVIEW)));
    }
}
