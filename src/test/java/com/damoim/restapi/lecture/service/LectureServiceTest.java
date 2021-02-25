package com.damoim.restapi.lecture.service;

import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.SaveLectureRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

@SpringBootTest
@Transactional
public class LectureServiceTest {

    @Autowired
    LectureService lectureService;

    @DisplayName("강의 추천 저장 테스트")
    @Test
    public void save() {
        SaveLectureRequest saveLectureRequest = SaveLectureRequest.builder().register("이경희").title("토비의 스프링 5").build();
        Lecture lecture = lectureService.save(saveLectureRequest);
        Assertions.assertEquals(saveLectureRequest.getTitle(), lecture.getTitle());
    }
}
