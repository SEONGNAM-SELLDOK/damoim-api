package com.damoim.restapi.bookreview.service;

import com.damoim.restapi.bookreview.dao.BookReviewRepository;
import com.damoim.restapi.bookreview.dao.BookReviewRepositorySupporter;
import com.damoim.restapi.bookreview.dao.BookReviewSaveRequestMapper;
import com.damoim.restapi.bookreview.dao.BookReviewUpdateRequestMapper;
import com.damoim.restapi.bookreview.entity.BookReview;
import com.damoim.restapi.bookreview.model.BookReviewGetRequest;
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
    private static String ROOT = "bookreview";

    public BookReview save(@Valid BookReviewSaveRequest saveRequest, MultipartFile multipartFile) {
        String imageUrl = null;
        if (Objects.nonNull(multipartFile)) {
            imageUrl = damoimFileUtil.upload(RequestFile.of(ROOT, multipartFile));
        }
        saveRequest.setImage(imageUrl);
        BookReview bookReview = saveRequestMapper.toEntity(saveRequest);
        return repository.save(bookReview);
    }

    @Transactional(readOnly = true)
    public Set<BookReview> getAll(Pageable pageable) {
        return repository.findAll(pageable).toSet();
    }

    public BookReview update(@Valid BookReviewUpdateRequest updateRequest, MultipartFile multipartFile) {
        String imageUrl = null;
        if (Objects.nonNull(multipartFile)) {
            imageUrl = damoimFileUtil.upload(RequestFile.of(ROOT, multipartFile));
        }
        updateRequest.setImage(imageUrl);
        BookReview bookReview = updateRequestMapper.toEntity(updateRequest);
        return repository.save(bookReview);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Set<BookReview> getByCondition(BookReviewGetRequest getRequest, Pageable pageable) {
        return repositorySupporter.search(getRequest, pageable);
    }
}
