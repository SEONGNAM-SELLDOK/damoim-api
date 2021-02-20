package com.damoim.restapi.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.damoim.restapi.member.dao.MemberMapper;
import com.damoim.restapi.member.model.SaveMemberRequest;
import com.damoim.restapi.member.model.SaveMemberResponse;
import com.damoim.restapi.member.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/**  * Controller
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
@Api(value = "member", description = "회원 관련 REST API")
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
}