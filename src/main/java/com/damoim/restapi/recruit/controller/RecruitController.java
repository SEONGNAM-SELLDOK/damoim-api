package com.damoim.restapi.recruit.controller;

import com.damoim.restapi.recruit.dao.RecruitSaveResponseDtoMapper;
import com.damoim.restapi.recruit.model.RecruitSaveRequestDto;
import com.damoim.restapi.recruit.model.RecruitSaveResponseDto;
import com.damoim.restapi.recruit.service.RecruitService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@Api(value = "recruit", description = "구인 구직 관련 REST API")
@RequiredArgsConstructor
@RequestMapping(value = "recruit")
@RestController
public class RecruitController {
    private final RecruitService recruitService;
    private final RecruitSaveResponseDtoMapper saveResponseDtoMapper;

    @PostMapping
    public ResponseEntity<RecruitSaveResponseDto> saveRecruit(@RequestBody RecruitSaveRequestDto saveRequestDto) {
        return new ResponseEntity<>(saveResponseDtoMapper.toDto(recruitService.save(saveRequestDto)), HttpStatus.CREATED);
    }
}
