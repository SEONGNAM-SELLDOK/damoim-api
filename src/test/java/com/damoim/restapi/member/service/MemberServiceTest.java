package com.damoim.restapi.member.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.SaveMemberRequest;

/**  * MemberServiceTest
 *
 * @author incheol.jung
 * @since 2021. 02. 20.
 */
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class MemberServiceTest {
	@Autowired
	MemberService memberService;

	@Test
	public void save(){
		SaveMemberRequest request = SaveMemberRequest.builder().name("incheol").build();
		Member member = memberService.save(request);
		Assertions.assertEquals(member.getName(), request.getName());
	}
}
