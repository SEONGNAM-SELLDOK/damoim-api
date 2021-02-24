package com.damoim.restapi.recruit.controller;

import com.damoim.restapi.recruit.dao.RecruitGetResponseMapper;
import com.damoim.restapi.recruit.dao.RecruitSaveResponseMapper;
import com.damoim.restapi.recruit.dao.RecruitUpdateResponseMapper;
import com.damoim.restapi.recruit.model.*;
import com.damoim.restapi.recruit.service.RecruitService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@Api(value = "recruits")
@RequiredArgsConstructor
@RequestMapping(value = "recruits")
@RestController
public class RecruitController {
    private final RecruitService recruitService;
    private final RecruitSaveResponseMapper saveResponseMapper;
    private final RecruitGetResponseMapper getResponseMapper;
    private final RecruitUpdateResponseMapper updateResponseMapper;

    @PostMapping
    public ResponseEntity<RecruitSaveResponse> saveRecruit(@Valid @RequestBody RecruitSaveRequest saveRequest) {
        return new ResponseEntity<>(saveResponseMapper.toDto(recruitService.save(saveRequest)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RecruitGetResponse> getRecruit(@Valid @PathVariable Long id) {
        return new ResponseEntity<>(getResponseMapper.toDto(recruitService.getRecruit(id)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RecruitUpdateResponse> updateRecruit(@Valid @RequestBody RecruitUpdateRequest updateRequest) {
        return new ResponseEntity<>(updateResponseMapper.toDto(recruitService.updateRecruit(updateRequest)), HttpStatus.OK);
    }
}
