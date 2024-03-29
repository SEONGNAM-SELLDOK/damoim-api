package com.damoim.restapi.recruit.service;

import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.recruit.model.RecruitGetRequest;
import com.damoim.restapi.recruit.model.RecruitResponse;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.model.RecruitUpdateRequest;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.recruit.model.*;
import com.damoim.restapi.reply.model.request.RequestSaveReply;
import com.damoim.restapi.reply.model.response.ResponseReply;
import com.damoim.restapi.reply.service.ReplyService;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@DisplayName("구인 구직 서비스 테스트")
@Transactional
@SpringBootTest
class RecruitServiceTest {

    @Autowired
    RecruitService recruitService;

    @Autowired
    ReplyService replyService;

    @DisplayName("구인 저장")
    @Test
    void saveRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(500).deadline(LocalDate.of(2022, 2, 1)).build();
        RecruitResponse recruit = recruitService.save(recruitSaveRequest, null);
        assertEquals(recruitSaveRequest.getTitle(), recruit.getTitle());
    }

    @DisplayName("구인 저장 Dto 검증")
    @Test
    void saveRecruitRequestValidation() {
        RecruitSaveRequest noCompanyDto = RecruitSaveRequest.builder().title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noCompanyDto, null));
        RecruitSaveRequest noTitleDto = RecruitSaveRequest.builder().company("Naver").reward(0).location("판교").deadline(LocalDate.of(2022, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noTitleDto, null));
        RecruitSaveRequest noDeadLineDto = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").reward(0).location("판교").build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noDeadLineDto, null));
        RecruitSaveRequest beforeDeadLineDto = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").reward(0).location("판교").deadline(LocalDate.of(2019, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(beforeDeadLineDto, null));
        RecruitSaveRequest noRewardDto = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").location("판교").deadline(LocalDate.of(2019, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noRewardDto, null));
        RecruitSaveRequest noLocationDto = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").reward(0).deadline(LocalDate.of(2019, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noLocationDto, null));
    }

    @DisplayName("구인 가져오기")
    @WithAccount("lokie")
    @Test
    void getRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(500).deadline(LocalDate.of(2022, 2, 1)).build();
        Long id = recruitService.save(recruitSaveRequest, null).getId();
        RecruitResponse recruit = recruitService.getById(id);
        assertEquals("Naver", recruit.getCompany());
        assertEquals("lokie", recruit.getRegister());
    }

    @DisplayName("구인 가져오기 해당 아이디 없음")
    @Test
    void getRecruitNoContentException() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(500).deadline(LocalDate.of(2022, 2, 1)).build();
        recruitService.save(recruitSaveRequest, null);
        Long id = 500L;
        assertThrows(RuntimeException.class, () -> recruitService.getById(id));
    }

    @DisplayName("구인 업데이트")
    @WithAccount("lokie")
    @Test
    void updateRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(500).deadline(LocalDate.of(2022, 2, 1)).build();
        RecruitResponse saveRecruit = recruitService.save(recruitSaveRequest, null);

        String updateCompany = "Never";
        String updateTitle = "회사 이름 바뀜";
        RecruitUpdateRequest recruitUpdateRequest = RecruitUpdateRequest.updateRequestBuilder().id(saveRecruit.getId()).company(updateCompany).title(updateTitle).location(saveRecruit.getLocation()).reward(saveRecruit.getReward()).deadline(saveRecruit.getDeadline()).build();
        RecruitResponse updateRecruit = recruitService.update(recruitUpdateRequest, null, AuthUser.builder().email("lokie").build());
        assertEquals(updateCompany, updateRecruit.getCompany());
        assertEquals(updateTitle, updateRecruit.getTitle());
    }

    @DisplayName("구인 업데이트 Dto 검증")
    @WithAccount("lokie")
    @Test
    void updateRecruitValidation() {
        AuthUser authUser = AuthUser.builder().email("lokie").build();
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().company("회사").title("구인 타이틀").location("판교").reward(500).deadline(LocalDate.of(2022, 2, 1)).build();
        RecruitResponse saveRecruit = recruitService.save(recruitSaveRequest, null);
        String updateCompany = "업데이트회사";
        RecruitUpdateRequest noIdRecruit = RecruitUpdateRequest.updateRequestBuilder().company(updateCompany).title(saveRecruit.getTitle()).reward(0).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noIdRecruit, null, authUser));
        RecruitUpdateRequest noCompanyRecruit = RecruitUpdateRequest.updateRequestBuilder().id(saveRecruit.getId()).title(saveRecruit.getTitle()).reward(0).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noCompanyRecruit, null, authUser));
        RecruitUpdateRequest noTitleRecruit = RecruitUpdateRequest.updateRequestBuilder().id(saveRecruit.getId()).company(updateCompany).reward(0).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noTitleRecruit, null, authUser));
        RecruitUpdateRequest noDeadLineRecruit = RecruitUpdateRequest.updateRequestBuilder().id(saveRecruit.getId()).company(updateCompany).title(saveRecruit.getTitle()).reward(0).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noDeadLineRecruit, null, authUser));
        RecruitUpdateRequest afterDeadLineRecruit = RecruitUpdateRequest.updateRequestBuilder().id(saveRecruit.getId()).company(updateCompany).title(saveRecruit.getTitle()).reward(0).deadline(LocalDate.of(2020, 1, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(afterDeadLineRecruit, null, authUser));
    }

    @DisplayName("구인 삭제")
    @WithAccount("lokie")
    @Test
    void deleteRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 1)).build();
        RecruitResponse saveRecruit = recruitService.save(recruitSaveRequest, null);
        RecruitResponse getRecruit = recruitService.getById(saveRecruit.getId());
        assertEquals(saveRecruit.getId(), getRecruit.getId());

        recruitService.delete(saveRecruit.getId(), AuthUser.builder().email("lokie").build());
        final Long id = saveRecruit.getId();
        assertThrows(RuntimeException.class, () -> recruitService.getById(id));
    }

    @DisplayName("구인 조건별 가져오기")
    @WithAccount("test@Email.com")
    @Test
    void getRecruits() {
        RecruitSaveRequest recruitSaveRequest1 = RecruitSaveRequest.builder().company("Firm").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 1)).build();
        RecruitSaveRequest recruitSaveRequest2 = RecruitSaveRequest.builder().company("Woowah").title("자바 개발자를 모집합니다.").location("잠실").reward(0).deadline(LocalDate.of(2022, 3, 3)).build();
        RecruitSaveRequest recruitSaveRequest3 = RecruitSaveRequest.builder().company("Naver").title("자바스크립트 개발자를 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 5)).build();
        RecruitSaveRequest recruitSaveRequest4 = RecruitSaveRequest.builder().company("Kakao").title("타입스크립트 개발자를 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 16)).build();
        RecruitSaveRequest recruitSaveRequest5 = RecruitSaveRequest.builder().company("Coupang").title("코틀린 개발자를 모집합니다.").location("잠실").reward(0).deadline(LocalDate.of(2022, 2, 20)).build();
        RecruitSaveRequest recruitSaveRequest6 = RecruitSaveRequest.builder().company("Coupang").title("자바 개발자를 모집합니다.").location("잠실").reward(0).deadline(LocalDate.of(2022, 2, 20)).build();
        RecruitSaveRequest recruitSaveRequest7 = RecruitSaveRequest.builder().company("Coupang").title("고랭 개발자를 모집합니다.").location("잠실").reward(0).deadline(LocalDate.of(2022, 2, 20)).build();
        recruitService.save(recruitSaveRequest1, null);
        recruitService.save(recruitSaveRequest2, null);
        recruitService.save(recruitSaveRequest3, null);
        recruitService.save(recruitSaveRequest4, null);
        recruitService.save(recruitSaveRequest5, null);
        recruitService.save(recruitSaveRequest6, null);
        recruitService.save(recruitSaveRequest7, null);
        PageRequest pageRequest = PageRequest.of(0, 6);
        Set<RecruitResponse> wooRecruit = recruitService.getRecruitByCondition(pageRequest, RecruitGetRequest.builder().company("Woo").build());
        assertEquals(1, wooRecruit.size());
        Set<RecruitResponse> couRecruit = recruitService.getRecruitByCondition(pageRequest, RecruitGetRequest.builder().company("Cou").build());
        assertEquals(3, couRecruit.size());
        Set<RecruitResponse> javaRecruit = recruitService.getRecruitByCondition(pageRequest, RecruitGetRequest.builder().title("자바 개발자를 모집합니다.").build());
        assertEquals(2, javaRecruit.size());
        Set<RecruitResponse> notConditionRecruitSet = recruitService.getRecruitByCondition(pageRequest, null);
        assertEquals(6, notConditionRecruitSet.size());
        Set<RecruitResponse> emptyConditionRecruitSet = recruitService.getRecruitByCondition(pageRequest, RecruitGetRequest.builder().title("").register("").build());
        assertEquals(6, emptyConditionRecruitSet.size());
    }

    @DisplayName("구인 댓글 입력하기")
    @Test
    void createRecruitWithReply() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().company("Firm").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 1)).build();
        RecruitResponse recruitResponse = recruitService.save(recruitSaveRequest, null);
        RequestSaveReply requestSaveReply = new RequestSaveReply();
        requestSaveReply.setWriter("쓰는자");
        requestSaveReply.setContent("내요오오오옹");
        requestSaveReply.setIsChildId(false);
        RequestSaveReply replySave = requestSaveReply.checkUrl("recruit");
        ResponseReply responseReply = replyService.replySave(recruitResponse.getId(), replySave);
        assertEquals("쓰는자", responseReply.getReply().getWriter());
    }

    @DisplayName("구인 댓글 가져오기")
    @Test
    void getRecruitWithReply() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().company("Firm").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 1)).build();
        RecruitResponse recruitResponse = recruitService.save(recruitSaveRequest, null);
        RequestSaveReply requestSaveReply = new RequestSaveReply();
        requestSaveReply.setWriter("쓰는자");
        requestSaveReply.setContent("내요오오오옹");
        requestSaveReply.setIsChildId(false);
        RequestSaveReply replySave = requestSaveReply.checkUrl("recruit");
        replyService.replySave(recruitResponse.getId(), replySave);
        RequestSaveReply requestSaveReply2 = new RequestSaveReply();
        requestSaveReply2.setWriter("쓰는자2");
        requestSaveReply2.setContent("내요오오오옹2");
        requestSaveReply2.setIsChildId(false);
        RequestSaveReply replySave2 = requestSaveReply2.checkUrl("recruit");
        replyService.replySave(recruitResponse.getId(), replySave2);

//        RequestSaveReply requestSaveReply3 = new RequestSaveReply();
//        requestSaveReply3.setWriter("대댓글쓰는자");
//        requestSaveReply3.setContent("잘나오나");
//        requestSaveReply3.setIsChildId(true);
//        requestSaveReply3.setParentReplyId(1L);
//        RequestSaveReply replySave3 = requestSaveReply3.checkUrl("recruit");
//        replyService.replySave(recruitResponse.getId(), replySave3);

        RecruitResponseWithReply recruitResponseWithReply = recruitService.getRecruitIncludeReply(recruitResponse.getId(), BoardType.RECRUIT);
        assertEquals(2, recruitResponseWithReply.getReplyList().size());
        assertEquals("쓰는자", recruitResponseWithReply.getReplyList().get(0).getWriter());

//        assertEquals(1, recruitResponseWithReply.getReplyList().get(1).getChildReply().size());
//        assertEquals("잘나오나", recruitResponseWithReply.getReplyList().get(1).getChildReply().get(0).getContent());

    }

}
