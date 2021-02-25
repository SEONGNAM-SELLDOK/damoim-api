package com.damoim.restapi;

import com.damoim.restapi.member.entity.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    @GetMapping
    public String index(
            @AuthenticationPrincipal Member member
    ) {
        // TODO remove me
        System.out.println(member.toString());

        return "/index";
    }
}