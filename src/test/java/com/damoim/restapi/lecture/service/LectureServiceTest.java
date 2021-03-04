package com.damoim.restapi.lecture.service;

import com.damoim.restapi.lecture.dao.LectureRepository;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.SaveLectureRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

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

    @Test
    public void 게시글저장_불러오기() {
        //given
        lectureService.save(SaveLectureRequest.builder()
                .title("테스트 게시글")
                .description("테스트 본문")
                .register("이경희")
                .build());

        //when
        //List<Lecture> lectureList = lectureRepository.findAll();

        //then
        //Lecture posts = lectureList.get(0);
        //assertThat(posts.getTitle(), is("테스트 게시글"));
        //assertThat(posts.getContent(), is("테스트 본문"));
    }
}
