package com.damoim.restapi.like.controller;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.like.dao.BoardLikeRepository;
import com.damoim.restapi.like.dao.BoardLikeSearchCondition;
import com.damoim.restapi.like.model.ChangeLikeRequest;
import com.damoim.restapi.like.model.ListLikeResponse;
import com.damoim.restapi.like.model.ReadLikeResponse;
import com.damoim.restapi.like.service.BoardLikeService;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author gisung.go
 * @since 2021-03-20
 */

@Slf4j
@Controller
@RequestMapping("like")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;
    private final MemberService memberService;


    @PostMapping
    @ResponseBody
    public ResponseEntity<ReadLikeResponse> changeLike(
            @AuthenticationPrincipal AuthUser member,
            final @Valid @RequestBody ChangeLikeRequest request) {
        Member nMember = memberService.findByName(member.getEmail());

        ReadLikeResponse response = boardLikeService.changeLike(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}/{type}")
    @ResponseBody
    public ResponseEntity<List<ReadLikeResponse>> findByLikeInfo(
            @PathVariable("id") Long id,
            @PathVariable("type") BoardType type) {
         List<ReadLikeResponse> byLikeInfo = boardLikeService.findByLikeInfo(id, type);
        return ResponseEntity.ok(byLikeInfo);
    }

    @GetMapping("pages")
    public ResponseEntity<Page<ListLikeResponse>> list(
            BoardLikeSearchCondition condition,
            Pageable pageable) {

        Page<ListLikeResponse> responses = boardLikeService.searchBoardLike(condition, pageable);
        return ResponseEntity.ok(responses);
    }
}
