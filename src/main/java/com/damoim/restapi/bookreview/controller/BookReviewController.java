package com.damoim.restapi.bookreview.controller;

import com.damoim.restapi.bookreview.entity.BookReview;
import com.damoim.restapi.bookreview.model.BookReviewGetRequest;
import com.damoim.restapi.bookreview.model.BookReviewSaveRequest;
import com.damoim.restapi.bookreview.model.BookReviewUpdateRequest;
import com.damoim.restapi.bookreview.service.BookReviewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<BookReview> create(@Valid @RequestBody BookReviewSaveRequest reviewSaveRequest, MultipartFile multipartFile) {
        return new ResponseEntity<>(bookReviewService.save(reviewSaveRequest, multipartFile), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookReview> update(@Valid @RequestBody BookReviewUpdateRequest reviewUpdateRequest, MultipartFile multipartFile) {
        return new ResponseEntity<>(bookReviewService.update(reviewUpdateRequest, multipartFile), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        bookReviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Set<BookReview>> getByCondition(@PageableDefault(size = 6, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable, BookReviewGetRequest getRequest) {
        return new ResponseEntity<>(bookReviewService.getByCondition(getRequest, pageable), HttpStatus.OK);
    }
}
