package com.damoim.restapi.auth.security;

import com.damoim.restapi.auth.JwtService;
import com.damoim.restapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

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

        JwtService.JwtUser jwtUser = jwtService.decode(((NotYetJwtAuthToken) authentication).getPrincipal());

        return new JwtAuthToken(memberService
                .get(jwtUser.getUserId())
                .orElseThrow(() -> new BadCredentialsException("Invalid jwt token."))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(NotYetJwtAuthToken.class);
    }
}
