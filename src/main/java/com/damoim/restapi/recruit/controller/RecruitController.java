package com.damoim.restapi.recruit.controller;

import com.damoim.restapi.recruit.dao.RecruitSaveResponseMapper;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.model.RecruitSaveResponse;
import com.damoim.restapi.recruit.service.RecruitService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final RecruitSaveResponseMapper saveResponseDtoMapper;

    @PostMapping
    public ResponseEntity<RecruitSaveResponse> saveRecruit(@Valid @RequestBody RecruitSaveRequest saveRequestDto) {
        return new ResponseEntity<>(saveResponseDtoMapper.toDto(recruitService.save(saveRequestDto)), HttpStatus.CREATED);
    }
}
