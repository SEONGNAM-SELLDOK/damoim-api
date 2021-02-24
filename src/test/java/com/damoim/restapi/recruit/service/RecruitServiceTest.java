package com.damoim.restapi.recruit.service;

import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
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
        Recruit recruit = recruitService.getRecruit(id);
        assertEquals("구인3", recruit.getRegister());
    }

    @DisplayName("구인 가져오기 해당 아이디 없음")
    @Test
    void getRecruitNoContentException() {
        RecruitSaveRequest recruitSaveRequest = RecruitSaveRequest.builder().register("오성록").company("Naver").title("서비스를 함께할 팀원을 모집합니다.").deadline(LocalDate.of(2022, 2, 1)).build();
        recruitService.save(recruitSaveRequest);
        Long id = 500L;
        assertThrows(RuntimeException.class, () -> recruitService.getRecruit(id));
    }
}
