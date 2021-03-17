package com.damoim.restapi.auth.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.AuthUser;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

public class JwtAuthToken extends AbstractAuthenticationToken {

	private final AuthUser authUser;

	public JwtAuthToken(Member member) {
		super(null);
		this.authUser = AuthUser.builder().name(member.getName()).email(member.getEmail()).build();
		setAuthenticated(true);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public AuthUser getPrincipal() {
		return authUser;
	}
}
