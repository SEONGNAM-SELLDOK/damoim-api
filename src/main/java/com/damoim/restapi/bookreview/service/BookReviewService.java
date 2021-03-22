package com.damoim.restapi.bookreview.service;

import com.damoim.restapi.bookreview.dao.*;
import com.damoim.restapi.bookreview.entity.BookReview;
import com.damoim.restapi.bookreview.model.BookReviewGetRequest;
import com.damoim.restapi.bookreview.model.BookReviewResponse;
import com.damoim.restapi.bookreview.model.BookReviewSaveRequest;
import com.damoim.restapi.bookreview.model.BookReviewUpdateRequest;
import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@Validated
@RequiredArgsConstructor
@Transactional
@Service
public class BookReviewService {
    private final BookReviewRepository repository;
    private final BookReviewRepositorySupporter repositorySupporter;
    private final DamoimFileUtil damoimFileUtil;
    private final BookReviewSaveRequestMapper saveRequestMapper;
    private final BookReviewUpdateRequestMapper updateRequestMapper;
    private final BookReviewResponseMapper responseMapper;
    private static String ROOT = "bookreview";

    public BookReviewResponse save(@Valid BookReviewSaveRequest saveRequest, MultipartFile multipartFile) {
        String imageUrl = null;
        if (Objects.nonNull(multipartFile)) {
            imageUrl = damoimFileUtil.upload(RequestFile.of(ROOT, multipartFile));
        }
        saveRequest.setImage(imageUrl);
        BookReview bookReview = saveRequestMapper.toEntity(saveRequest);
        return responseMapper.toDto(repository.save(bookReview));
    }

    @Transactional(readOnly = true)
    public Set<BookReviewResponse> getAll(Pageable pageable) {
        return bookReviewResponseSet(repository.findAll(pageable).toSet());
    }

    public BookReviewResponse update(@Valid BookReviewUpdateRequest updateRequest, MultipartFile multipartFile) {
        String imageUrl = null;
        if (Objects.nonNull(multipartFile)) {
            imageUrl = damoimFileUtil.upload(RequestFile.of(ROOT, multipartFile));
        }
        updateRequest.setImage(imageUrl);
        BookReview bookReview = updateRequestMapper.toEntity(updateRequest);
        return responseMapper.toDto(repository.save(bookReview));
    }

    public void delete(long id) {
        BookReview bookReview = repository.findById(id).orElseThrow(RuntimeException::new);
        repository.delete(bookReview);
    }

    @Transactional(readOnly = true)
    public BookReviewResponse getById(long id) {
        return responseMapper.toDto(repository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Transactional(readOnly = true)
    public Set<BookReviewResponse> getByCondition(BookReviewGetRequest getRequest, Pageable pageable) {
        return bookReviewResponseSet(repositorySupporter.search(getRequest, pageable));
    }

    private Set<BookReviewResponse> bookReviewResponseSet(Set<BookReview> bookReviewSet) {
        return bookReviewSet.stream().map(responseMapper::toDto).collect(Collectors.toSet());
    }
}
