package com.damoim.restapi.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.damoim.restapi.employee.entity.Member;
import com.damoim.restapi.employee.model.SaveMemberRequest;
import com.damoim.restapi.employee.service.MemberService;
import lombok.RequiredArgsConstructor;

/**  * Controller
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<Member> save(@RequestBody SaveMemberRequest saveMemberRequest){
		return new ResponseEntity<>(memberService.save(saveMemberRequest), HttpStatus.CREATED);
	}
}
