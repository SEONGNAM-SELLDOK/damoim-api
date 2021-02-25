package com.damoim.restapi.auth.security;

import com.damoim.restapi.auth.JwtService;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null || !supports(authentication.getClass()))
            return null;

        JwtService.JwtUser jwtUser = jwtService.decode(((NotYetJwtAuthToken) authentication).getPrincipal());

        Member member = memberService.get(jwtUser.getUserId()).orElseThrow(() -> new RuntimeException("auth error"));

        return new JwtAuthToken(member);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(NotYetJwtAuthToken.class);
    }
}
