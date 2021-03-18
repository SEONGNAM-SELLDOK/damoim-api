package com.damoim.restapi.member.service;

import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.SaveMemberRequest;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class MemberServiceTest {
	@Autowired
	MemberService memberService;

	@Test
	@WithAccount("test@Email.com")
	public void save(){
		SaveMemberRequest request = SaveMemberRequest.builder().name("incheol").build();
		Member member = memberService.save(request);
		Assertions.assertEquals(member.getName(), request.getName());
	}
}
