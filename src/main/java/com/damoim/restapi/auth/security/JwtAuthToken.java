package com.damoim.restapi.auth.security;

import com.damoim.restapi.member.entity.Member;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthToken extends AbstractAuthenticationToken {

    private final Member member;

    public JwtAuthToken(Member member) {
        super(null);
        this.member = member;
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
    public Member getPrincipal() {
        return member;
    }
}
