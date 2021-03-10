package com.damoim.restapi.auth.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.damoim.restapi.member.entity.Member;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

public class JwtAuthToken extends UsernamePasswordAuthenticationToken {

    public JwtAuthToken(Member member) {
        super(member.getName(), member.getEmail());
    }
}
