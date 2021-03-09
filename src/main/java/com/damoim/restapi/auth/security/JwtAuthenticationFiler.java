package com.damoim.restapi.auth.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@RequiredArgsConstructor
public class JwtAuthenticationFiler implements Filter {

    public final AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {

            if (httpServletRequest.getCookies() == null) {
                chain.doFilter(request, response);
                return;
            }

            Optional<Cookie> optionalCookie = Arrays.stream(httpServletRequest.getCookies()).filter(c -> "AUTH_TOKEN".equals(c.getName())).findFirst();

            if (optionalCookie.isEmpty()) {
                chain.doFilter(request, response);
                return;
            }

            NotYetJwtAuthToken notYetJwtAuthToken = new NotYetJwtAuthToken(optionalCookie.get().getValue());
            Authentication result = authenticationManager.authenticate(notYetJwtAuthToken);

            SecurityContextHolder.getContext().setAuthentication(result);

            chain.doFilter(request, response);

        } catch (BadCredentialsException e) {
            // 이상한 토큰은 지워준다.
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            Cookie cookie = new Cookie("AUTH_TOKEN", null);
            cookie.setMaxAge(0);
            httpServletResponse.addCookie(cookie);
            // TODO 어떻게 리다이렉트 하는게 좋을까?
            throw new BadCredentialsException("Invalid access token");
        } catch (IOException | ServletException e) {
            throw new BadCredentialsException("JwtAuthenticationFiler error");
        }
    }
}