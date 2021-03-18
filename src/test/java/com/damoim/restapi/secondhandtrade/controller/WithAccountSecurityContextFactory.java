package com.damoim.restapi.secondhandtrade.controller;

import com.damoim.restapi.member.model.AuthUser;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String email = withAccount.value();

        AuthUser authUser = new AuthUser("testUserName", email);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            authUser, "test", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}