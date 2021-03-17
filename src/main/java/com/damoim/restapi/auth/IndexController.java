package com.damoim.restapi.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.damoim.restapi.member.model.AuthUser;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@RestController
public class IndexController {

    @GetMapping("/me")
    public String index(@AuthenticationPrincipal AuthUser member) {

        return "안뇽~ " + member.getName() + "(" + member.getEmail() + ")";
    }
}
