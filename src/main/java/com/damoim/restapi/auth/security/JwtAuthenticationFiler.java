package com.damoim.restapi.auth.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFiler implements Filter {

    public final AuthenticationManager am;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest r = (HttpServletRequest) request;
        try {
            Optional<Cookie> optionalCookie = Arrays.stream(r.getCookies()).filter(c -> "AUTH_TOKEN".equals(c.getName())).findFirst();

            if (optionalCookie.isEmpty()) {
                chain.doFilter(request, response);
                return;
            }

            NotYetJwtAuthToken at = new NotYetJwtAuthToken(optionalCookie.get().getValue());

            Authentication result = am.authenticate(at);
            SecurityContextHolder.getContext().setAuthentication(result);
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            throw new RuntimeException("AUTH error");
        }
    }
}
