package com.damoim.restapi.auth;

import com.damoim.restapi.member.entity.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@RestController
public class IndexController {

    @GetMapping("/me")
    public String index(@AuthenticationPrincipal Member member) {

        return "안뇽~ " + member.getName();
    }
}