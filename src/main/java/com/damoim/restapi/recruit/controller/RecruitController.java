package com.damoim.restapi.recruit.controller;

import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.recruit.model.RecruitGetRequest;
import com.damoim.restapi.recruit.model.RecruitResponse;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.model.RecruitUpdateRequest;
import com.damoim.restapi.recruit.service.RecruitService;
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
 * @since 2021/02/21
 */
@Api(value = "recruits", tags = "구인 관련 REST API")
@RequiredArgsConstructor
@RequestMapping(value = "recruits")
@RestController
public class RecruitController {
    private final RecruitService recruitService;
    private static final String ROOT = "recruit";

    @PostMapping
    public ResponseEntity<RecruitResponse> saveRecruit(@Valid @RequestBody RecruitSaveRequest saveRequest, @RequestParam(required = false) MultipartFile file) {
        return new ResponseEntity<>(recruitService.save(saveRequest, RequestFile.of(ROOT, file)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RecruitResponse> getRecruit(@Valid @PathVariable Long id) {
        return new ResponseEntity<>(recruitService.getById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RecruitResponse> updateRecruit(@Valid @RequestBody RecruitUpdateRequest updateRequest, @RequestParam(required = false) MultipartFile file, @AuthenticationPrincipal AuthUser authUser) {
        return new ResponseEntity<>(recruitService.update(updateRequest, RequestFile.of(ROOT, file), authUser), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecruit(@Valid @PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) {
        recruitService.delete(id, authUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Set<RecruitResponse>> getRecruits(@PageableDefault(size = 6, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, RecruitGetRequest getRequest) {
        return new ResponseEntity<>(recruitService.getRecruitByCondition(pageable, getRequest), HttpStatus.OK);
    }
}
