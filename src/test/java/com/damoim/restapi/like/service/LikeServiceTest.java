package com.damoim.restapi.like.service;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.like.entity.BoardLike;
import com.damoim.restapi.like.model.ReadLikeResponse;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("좋아요 기능 테스트")
@SpringBootTest
@Transactional
public class LikeServiceTest {

    @Autowired
    BoardLikeService boardLikeService;

    @Autowired
    MemberService memberService;

    @Test
    void saveLikeTest(@AuthenticationPrincipal AuthUser member) {
        Member nMember = memberService.findByName(member.getEmail());
        BoardLike boardLike = BoardLike.builder()
                .memberLike(nMember)
                .boardId(1L)
                .boardCount(0)
                .boardType(BoardType.SEMINAR)
                .build();

        ReadLikeResponse readLikeResponse = boardLikeService.saveLike(boardLike);
        assertEquals(readLikeResponse, boardLike);
    }

}
