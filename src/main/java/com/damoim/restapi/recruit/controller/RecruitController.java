package com.damoim.restapi.recruit.controller;

import com.damoim.restapi.recruit.dao.RecruitResponseMapper;
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
    private final RecruitResponseMapper responseMapper;

    @PostMapping
    public ResponseEntity<RecruitResponse> saveRecruit(@Valid @RequestBody RecruitSaveRequest saveRequest) {
        return new ResponseEntity<>(responseMapper.toDto(recruitService.save(saveRequest)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RecruitResponse> getRecruit(@Valid @PathVariable Long id) {
        return new ResponseEntity<>(responseMapper.toDto(recruitService.getById(id)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RecruitResponse> updateRecruit(@Valid @RequestBody RecruitUpdateRequest updateRequest) {
        return new ResponseEntity<>(responseMapper.toDto(recruitService.update(updateRequest)), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecruit(@Valid @PathVariable Long id) {
        recruitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
