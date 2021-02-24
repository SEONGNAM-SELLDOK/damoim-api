package com.damoim.restapi.recruit.service;

import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.model.RecruitUpdateRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("작성자").company("회사").title("구인 타이틀").deadline(LocalDate.of(2022, 2, 1)).build();
        Recruit saveRecruit = recruitService.save(recruitSaveRequest);
        String updateCompany = "업데이트회사";
        RecruitUpdateRequest noIdRecruit = RecruitUpdateRequest.builder().register(saveRecruit.getRegister()).company(updateCompany).title(saveRecruit.getTitle()).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noIdRecruit));
        RecruitUpdateRequest noRegisterRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).company(updateCompany).title(saveRecruit.getTitle()).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noRegisterRecruit));
        RecruitUpdateRequest noCompanyRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).title(saveRecruit.getTitle()).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noCompanyRecruit));
        RecruitUpdateRequest noTitleRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).company(updateCompany).deadline(saveRecruit.getDeadline()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noTitleRecruit));
        RecruitUpdateRequest noDeadLineRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).company(updateCompany).title(saveRecruit.getTitle()).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(noDeadLineRecruit));
        RecruitUpdateRequest afterDeadLineRecruit = RecruitUpdateRequest.builder().id(saveRecruit.getId()).register(saveRecruit.getRegister()).company(updateCompany).title(saveRecruit.getTitle()).deadline(LocalDate.of(2020, 1, 1)).build();
        assertThrows(RuntimeException.class, () -> recruitService.update(afterDeadLineRecruit));
    }

    @DisplayName("구인 삭제")
    @Test
    void deleteRecruit() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("오성록").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2022, 2, 1)).build();
        Recruit saveRecruit = recruitService.save(recruitSaveRequest);
        Recruit getRecruit = recruitService.getById(saveRecruit.getId());
        assertEquals(saveRecruit.getId(), getRecruit.getId());

        recruitService.delete(saveRecruit.getId());
        assertThrows(RuntimeException.class, () -> recruitService.getById(saveRecruit.getId()));
    }
}
