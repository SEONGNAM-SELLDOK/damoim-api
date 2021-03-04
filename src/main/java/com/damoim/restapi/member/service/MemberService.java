package com.damoim.restapi.member.service;

import com.damoim.restapi.member.dao.MemberRepository;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.SaveMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberService
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
