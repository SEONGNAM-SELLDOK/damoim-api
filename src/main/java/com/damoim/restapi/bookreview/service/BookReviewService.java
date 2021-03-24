package com.damoim.restapi.bookreview.service;

import com.damoim.restapi.bookreview.dao.*;
import com.damoim.restapi.bookreview.entity.BookReview;
import com.damoim.restapi.bookreview.model.BookReviewGetRequest;
import com.damoim.restapi.bookreview.model.BookReviewResponse;
import com.damoim.restapi.bookreview.model.BookReviewSaveRequest;
import com.damoim.restapi.bookreview.model.BookReviewUpdateRequest;
import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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

    public BookReviewResponse save(@Valid BookReviewSaveRequest saveRequest, RequestFile file) {
        String imageUrl = null;
        if (Objects.nonNull(file)) {
            imageUrl = damoimFileUtil.upload(file);
        }
        saveRequest.setImage(imageUrl);
        BookReview bookReview = saveRequestMapper.toEntity(saveRequest);
        return responseMapper.toDto(repository.save(bookReview));
    }

    @Transactional(readOnly = true)
    public Set<BookReviewResponse> getAll(Pageable pageable) {
        return bookReviewResponseSet(repository.findAll(pageable).toSet());
    }

    public BookReviewResponse update(@Valid BookReviewUpdateRequest updateRequest, RequestFile file, AuthUser authUser) {
        BookReview origin = getBookReviewById(updateRequest.getId());
        validateEditor(origin, authUser);
        BookReview updateBookReview = updateRequestMapper.toEntity(updateRequest);
        String imageUrl = null;
        if (Objects.nonNull(file)) {
            imageUrl = damoimFileUtil.upload(file);
        }
        updateBookReview.setImage(imageUrl);
        return responseMapper.toDto(repository.save(updateBookReview));
    }

    public void delete(long id, AuthUser authUser) {
        BookReview bookReview = repository.findById(id).orElseThrow(RuntimeException::new);
        validateEditor(bookReview, authUser);
        repository.delete(bookReview);
    }

    private void validateEditor(BookReview bookReview, AuthUser authUser) {
        if (Objects.isNull(bookReview) || Objects.isNull(authUser) || !bookReview.isRegister(authUser.getEmail())) {
            throw new RuntimeException();
        }
    }

    @Transactional(readOnly = true)
    public BookReviewResponse getById(long id) {
        return responseMapper.toDto(getBookReviewById(id));
    }

    private BookReview getBookReviewById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundResource(HttpStatus.NOT_FOUND.toString(), String.valueOf(id)));
    }

    @Transactional(readOnly = true)
    public Set<BookReviewResponse> getByCondition(BookReviewGetRequest getRequest, Pageable pageable) {
        return bookReviewResponseSet(repositorySupporter.search(getRequest, pageable));
    }

    private Set<BookReviewResponse> bookReviewResponseSet(Set<BookReview> bookReviewSet) {
        return bookReviewSet.stream().map(responseMapper::toDto).collect(Collectors.toSet());
    }
}
