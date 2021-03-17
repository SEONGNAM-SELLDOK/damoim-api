package com.damoim.restapi.member.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.damoim.restapi.member.dao.MemberMapper;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.member.model.GetMemberResponse;
import com.damoim.restapi.member.model.SaveMemberRequest;
import com.damoim.restapi.member.model.SaveMemberResponse;
import com.damoim.restapi.member.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**
 * Controller
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
@Api(value = "members", tags = "회원 관련 REST API")
@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping
    public ResponseEntity<SaveMemberResponse> save(@RequestBody SaveMemberRequest saveMemberRequest) {
        return new ResponseEntity<>(memberMapper.toDto(memberService.save(saveMemberRequest)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GetMemberResponse> get(
        @AuthenticationPrincipal AuthUser member
    ) {
        GetMemberResponse response = new GetMemberResponse();
        BeanUtils.copyProperties(member, response, GetMemberResponse.class);
        return ResponseEntity.ok(response);
    }
}
