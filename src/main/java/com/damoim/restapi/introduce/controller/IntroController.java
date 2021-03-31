package com.damoim.restapi.introduce.controller;

import com.damoim.restapi.introduce.model.IntroItemResponse;
import com.damoim.restapi.introduce.model.IntroSaveRequest;
import com.damoim.restapi.introduce.model.IntroUpdateRequest;
import com.damoim.restapi.introduce.service.IntroService;
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

import javax.validation.Valid;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@Api(value = "커뮤니티 소개")
@RequiredArgsConstructor
@RequestMapping(value = "intro")
@RestController
public class IntroController {

    private final IntroService introService;

    @PostMapping
    public ResponseEntity<IntroItemResponse> save(@Valid @RequestBody IntroSaveRequest saveRequest) {
        return new ResponseEntity<>(new IntroItemResponse(introService.create(saveRequest)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntroItemResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(new IntroItemResponse(introService.getById(id)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<IntroItemResponse> update(@Valid @RequestBody IntroUpdateRequest updateRequest, @AuthenticationPrincipal AuthUser authUser) {
        introService.update(updateRequest, authUser);
        return new ResponseEntity<>(new IntroItemResponse(introService.getById(updateRequest.getId())), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) {
        introService.delete(id, authUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<IntroItemResponse> getAll(@PageableDefault(sort = "createdDate", direction = Sort.Direction.ASC, size = 6) Pageable pageable) {
        return new ResponseEntity<>(new IntroItemResponse(introService.getAll(pageable)), HttpStatus.OK);

    }
}
