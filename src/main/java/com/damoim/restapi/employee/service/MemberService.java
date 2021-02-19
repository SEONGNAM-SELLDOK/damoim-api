package com.damoim.restapi.employee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.damoim.restapi.employee.dao.MemberRepository;
import com.damoim.restapi.employee.entity.Member;
import com.damoim.restapi.employee.model.SaveMemberRequest;
import lombok.RequiredArgsConstructor;

/**  * MemberService
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public Member save(SaveMemberRequest request) {
		Member member = Member.builder()
			.id(request.getId())
			.name(request.getName())
			.pwd(request.getPwd())
			.profilePicUrl(request.getProfilePicUrl())
			.register(request.getRegister())
			.build();

		return memberRepository.save(member);
	}
}
