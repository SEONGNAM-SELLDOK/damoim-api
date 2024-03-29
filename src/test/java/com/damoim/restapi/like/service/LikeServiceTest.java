package com.damoim.restapi.like.service;

import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.member.dao.MemberRepository;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

@DisplayName("like test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class LikeServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ObjectMapper objectMapper;

    Board board;
    @BeforeEach()
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        board = Board.builder()
                .title("타이틀")
                .content("내용")
                .address(new Address("KR", "SEOUL", "강남대로 1111"))
                .totalMember(20)
                .currentMember(5)
                .subject("객체지향")
                .damoimTag(new DamoimTag("JAVA"))
                .endDate(LocalDateTime.now())
                .boardType(BoardType.SEMINAR)
                .build();

    }
}
