package com.damoim.restapi.introduce.service;

import com.damoim.restapi.introduce.model.IntroResponse;
import com.damoim.restapi.introduce.model.IntroSaveRequest;
import com.damoim.restapi.introduce.model.IntroUpdateRequest;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@DisplayName("커뮤니티 소개글 테스트")
@Transactional
@SpringBootTest
class IntroServiceTest {

    @Autowired
    IntroService introService;

    @DisplayName("커뮤니티 소개글 생성 테스트")
    @Test
    void createTest() {
        IntroSaveRequest saveRequest = IntroSaveRequest.builder().content("만나서 반갑습니다.").build();
        IntroResponse introResponse = introService.create(saveRequest);
        assertEquals("만나서 반갑습니다.", introResponse.getContent());
    }

    @DisplayName("커뮤니티 소개글 가져오기 테스트")
    @Test
    void getTest() {
        IntroSaveRequest saveRequest = IntroSaveRequest.builder().content("만나서 반갑습니다.").build();
        IntroResponse introResponse = introService.create(saveRequest);
        IntroResponse getIntro = introService.getById(introResponse.getId());
        assertEquals("만나서 반갑습니다.", getIntro.getContent());
    }

    @DisplayName("커뮤니티 소개글 업데이트 테스트")
    @WithAccount("lokie")
    @Test
    void updateTest() {
        IntroSaveRequest saveRequest = IntroSaveRequest.builder().content("만나서 반갑습니다.").build();
        IntroResponse introResponse = introService.create(saveRequest);
        final String updateContent = "(수정) 만나서 좋아요.";
        IntroUpdateRequest updateRequest = IntroUpdateRequest.updateBuilder().id(introResponse.getId()).content(updateContent).build();
        introService.update(updateRequest, AuthUser.builder().email("lokie").build());

        IntroResponse getIntro = introService.getById(introResponse.getId());
        assertEquals(updateContent, getIntro.getContent());
    }

    @DisplayName("커뮤니티 소개글 삭제 테스트")
    @WithAccount("lokie")
    @Test
    void deleteTest() {
        IntroSaveRequest saveRequest = IntroSaveRequest.builder().content("만나서 반갑습니다.").build();
        IntroResponse introResponse = introService.create(saveRequest);
        introService.delete(introResponse.getId(), AuthUser.builder().email("lokie").build());
        Long id = introResponse.getId();
        assertThrows(NotFoundResource.class, () -> introService.getById(id));
    }

}
