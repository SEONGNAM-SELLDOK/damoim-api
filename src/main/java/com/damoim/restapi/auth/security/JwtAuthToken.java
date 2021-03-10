package com.damoim.restapi.auth.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.TestUser;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

public class JwtAuthToken extends AbstractAuthenticationToken {

    private final TestUser member;

    public JwtAuthToken(Member member) {
        super(null);
        this.member = new TestUser(member.getName(), member.getEmail());
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
    public TestUser getPrincipal() {
        return member;
    }
}
