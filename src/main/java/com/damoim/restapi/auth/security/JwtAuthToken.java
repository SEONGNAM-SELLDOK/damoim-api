package com.damoim.restapi.auth.security;

import com.damoim.restapi.member.entity.Member;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

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
