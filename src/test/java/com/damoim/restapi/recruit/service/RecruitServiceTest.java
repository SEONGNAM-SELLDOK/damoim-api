package com.damoim.restapi.recruit.service;

import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitGetRequest;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.model.RecruitUpdateRequest;
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

    @DisplayName("구인 저장")
    @Test
    void saveRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("오성록").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2022, 2, 1)).build();
        Recruit recruit = recruitService.save(recruitSaveRequest);
        assertEquals(recruitSaveRequest.getTitle(), recruit.getTitle());
    }

    @DisplayName("구인 저장 Dto 검증")
    @Test
    void saveRecruitRequestValidation() {
        RecruitSaveRequest noRegisterDto = RecruitSaveRequest.builder().company("Naver").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2022, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noRegisterDto));
        RecruitSaveRequest noCompanyDto = RecruitSaveRequest.builder().register("오성록").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2022, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noCompanyDto));
        RecruitSaveRequest noTitleDto = RecruitSaveRequest.builder().register("오성록").company("Naver").deadline(LocalDate.of(2022, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noTitleDto));
        RecruitSaveRequest noDeadLineDto = RecruitSaveRequest.builder().register("오성록").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").build();
        assertThrows(RuntimeException.class, () -> recruitService.save(noDeadLineDto));
        RecruitSaveRequest beforeDeadLineDto = RecruitSaveRequest.builder().register("오성록").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2019, 2, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.save(beforeDeadLineDto));
    }

    @DisplayName("구인 가져오기")
    @Test
    void getRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("구인3").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2022, 2, 1)).build();
        Long id = recruitService.save(recruitSaveRequest).getId();
        Recruit recruit = recruitService.getById(id);
        assertEquals("구인3", recruit.getRegister());
    }

    @DisplayName("구인 가져오기 해당 아이디 없음")
    @Test
    void getRecruitNoContentException() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("오성록").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2022, 2, 1)).build();
        recruitService.save(recruitSaveRequest);
        Long id = 500L;
        assertThrows(RuntimeException.class, () -> recruitService.getById(id));
    }

    @DisplayName("구인 업데이트")
    @Test
    void updateRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("오성록").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2022, 2, 1)).build();
        Recruit saveRecruit = recruitService.save(recruitSaveRequest);

        String updateCompany = "Never";
        String updateTitle = "회사 이름 바뀜";
        RecruitUpdateRequest recruitUpdateRequest = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).company(updateCompany).title(updateTitle).deadline(saveRecruit.getDeadline()).build();
        Recruit updateRecruit = recruitService.update(recruitUpdateRequest);
        assertEquals(updateCompany, updateRecruit.getCompany());
        assertEquals(updateTitle, updateRecruit.getTitle());
    }

    @DisplayName("구인 업데이트 Dto 검증")
    @Test
    void updateRecruitValidation() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("작성자").company("회사").title("구인 타이틀").location("판교").reward(500).deadline(LocalDate.of(2022, 2, 1)).build();
        Recruit saveRecruit = recruitService.save(recruitSaveRequest);
        String updateCompany = "업데이트회사";
        RecruitUpdateRequest noIdRecruit = RecruitUpdateRequest.builder().register(saveRecruit.getRegister()).company(updateCompany).title(saveRecruit.getTitle()).reward(0).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noIdRecruit));
        RecruitUpdateRequest noRegisterRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).company(updateCompany).title(saveRecruit.getTitle()).reward(0).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noRegisterRecruit));
        RecruitUpdateRequest noCompanyRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).title(saveRecruit.getTitle()).reward(0).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noCompanyRecruit));
        RecruitUpdateRequest noTitleRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).company(updateCompany).reward(0).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noTitleRecruit));
        RecruitUpdateRequest noDeadLineRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).company(updateCompany).title(saveRecruit.getTitle()).reward(0).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noDeadLineRecruit));
        RecruitUpdateRequest afterDeadLineRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).company(updateCompany).title(saveRecruit.getTitle()).reward(0).deadline(LocalDate.of(2020, 1, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(afterDeadLineRecruit));
    }

    @DisplayName("구인 삭제")
    @Test
    void deleteRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("오성록").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 1)).build();
        Recruit saveRecruit = recruitService.save(recruitSaveRequest);
        Recruit getRecruit = recruitService.getById(saveRecruit.getId());
        assertEquals(saveRecruit.getId(), getRecruit.getId());

        recruitService.delete(saveRecruit.getId());
        assertThrows(RuntimeException.class, () -> recruitService.getById(saveRecruit.getId()));
    }

    @DisplayName("구인 조건별 가져오기")
    @Test
    void getRecruits() {
        RecruitSaveRequest recruitSaveRequest1 = RecruitSaveRequest.builder().register("오성록").company("Firm").title("서비스를 함께할 팀원을 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 1)).build();
        RecruitSaveRequest recruitSaveRequest2 = RecruitSaveRequest.builder().register("배민").company("Woowah").title("자바 개발자를 모집합니다.").location("잠실").reward(0).deadline(LocalDate.of(2022, 3, 3)).build();
        RecruitSaveRequest recruitSaveRequest3 = RecruitSaveRequest.builder().register("네이버").company("Naver").title("자바스크립트 개발자를 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 5)).build();
        RecruitSaveRequest recruitSaveRequest4 = RecruitSaveRequest.builder().register("카카오").company("Kakao").title("타입스크립트 개발자를 모집합니다.").location("판교").reward(0).deadline(LocalDate.of(2022, 2, 16)).build();
        RecruitSaveRequest recruitSaveRequest5 = RecruitSaveRequest.builder().register("쿠팡").company("Coupang").title("코틀린 개발자를 모집합니다.").location("잠실").reward(0).deadline(LocalDate.of(2022, 2, 20)).build();
        recruitService.save(recruitSaveRequest1);
        recruitService.save(recruitSaveRequest2);
        recruitService.save(recruitSaveRequest3);
        recruitService.save(recruitSaveRequest4);
        recruitService.save(recruitSaveRequest5);
        PageRequest pageRequest = PageRequest.of(0, 6);
        Set<Recruit> recruitSet = recruitService.getRecruitByCondition(pageRequest, RecruitGetRequest.builder().company("Woo").build());
        assertEquals(1, recruitSet.size());
        Set<Recruit> notConditionRecruitSet = recruitService.getRecruitByCondition(pageRequest, null);
        assertEquals(5, notConditionRecruitSet.size());

    }
}
