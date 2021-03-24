package com.damoim.restapi.bookreview.controller;

import com.damoim.restapi.bookreview.model.BookReviewGetRequest;
import com.damoim.restapi.bookreview.model.BookReviewResponse;
import com.damoim.restapi.bookreview.model.BookReviewSaveRequest;
import com.damoim.restapi.bookreview.model.BookReviewUpdateRequest;
import com.damoim.restapi.bookreview.service.BookReviewService;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.member.model.AuthUser;
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
import java.util.Set;

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
    private static final String ROOT = "bookreview";

    @PostMapping
    public ResponseEntity<BookReviewResponse> create(@Valid @RequestBody BookReviewSaveRequest reviewSaveRequest, MultipartFile multipartFile) {
        return new ResponseEntity<>(bookReviewService.save(reviewSaveRequest, RequestFile.of(ROOT, multipartFile)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookReviewResponse> update(@Valid @RequestBody BookReviewUpdateRequest reviewUpdateRequest, MultipartFile multipartFile, @AuthenticationPrincipal AuthUser authUser) {
        return new ResponseEntity<>(bookReviewService.update(reviewUpdateRequest, RequestFile.of(ROOT, multipartFile), authUser), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) {
        bookReviewService.delete(id, authUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Set<BookReviewResponse>> getByCondition(@PageableDefault(size = 6, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable, BookReviewGetRequest getRequest) {
        return new ResponseEntity<>(bookReviewService.getByCondition(getRequest, pageable), HttpStatus.OK);
    }
}
