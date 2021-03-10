package com.damoim.restapi.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.damoim.restapi.auth.JwtService;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final JwtService jwtService;
	private final MemberService memberService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication == null || !supports(authentication.getClass()))
			return null;
		String token = ((NotYetJwtAuthToken)authentication).getPrincipal();
		Member member = memberService.get(jwtService.decode(token).getUserId())
			.orElseThrow(() -> new BadCredentialsException("Invalid jwt token."));

		return new JwtAuthToken(member);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(NotYetJwtAuthToken.class);
	}
}
