package com.damoim.restapi.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("naver/callback")
    public String naverCallback(
            @PathParam("state") String state, // oauth state는 고려하지 않았습니다 ㅎㅎ
            @PathParam("code") String code,
            HttpServletResponse httpServletResponse
    ) {
        String authToken = authService.naverCallback(code);
        setAuthToken(httpServletResponse, authToken);

        return "redirect:http://localhost:3000";
    }

    private void setAuthToken(HttpServletResponse httpServletResponse, String authToken) {
        Cookie cookie = new Cookie("AUTH_TOKEN", authToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // cookie.setSecure(true); FIXME https only

        httpServletResponse.addCookie(cookie);
    }
}
