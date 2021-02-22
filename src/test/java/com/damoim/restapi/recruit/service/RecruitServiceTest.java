package com.damoim.restapi.recruit.service;

import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@DisplayName("구인 구직 서비스 테스트")
@Transactional
@SpringBootTest
public class RecruitServiceTest {

    @Autowired
    RecruitService recruitService;

    @DisplayName("구인 구직 저장")
    @Test
    void saveRecruit() {
        RecruitSaveRequestDto recruitSaveRequestDto = RecruitSaveRequestDto.builder().title("서비스를 함께할 팀원을 모집합니다.").build();
        Recruit recruit = recruitService.save(recruitSaveRequestDto);
        Assertions.assertEquals(recruitSaveRequestDto.getTitle(), recruit.getTitle());
    }
}
