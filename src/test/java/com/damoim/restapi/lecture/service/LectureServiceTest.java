package com.damoim.restapi.lecture.service;

import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.entity.LectureSubject;
import com.damoim.restapi.lecture.model.LectureGetRequest;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("강의 테스트")
@SpringBootTest
@Transactional
public class LectureServiceTest {

    @Autowired
    LectureService lectureService;

    @DisplayName("강의 추천 저장 테스트")
    @Test
    void saveTest() {
        LectureSaveRequest lectureSaveRequest = LectureSaveRequest.builder().speaker("오성록").register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        Lecture lecture = lectureService.save(lectureSaveRequest, null);
        assertEquals(lectureSaveRequest.getTitle(), lecture.getTitle());
    }

    @DisplayName("강의 저장 테스트 Request 검증")
    @Test
    void saveRequestValidate() {
        LectureSaveRequest noSpeaker = LectureSaveRequest.builder().register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noSpeaker, null));
        LectureSaveRequest noRegister = LectureSaveRequest.builder().speaker("오성록").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noRegister, null));
        LectureSaveRequest noTitle = LectureSaveRequest.builder().speaker("오성록").register("이경희").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noTitle, null));
        LectureSaveRequest noSubject = LectureSaveRequest.builder().speaker("오성록").register("이경희").title("토비의 스프링 5").deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noSubject, null));
        LectureSaveRequest noDeadLine = LectureSaveRequest.builder().speaker("오성록").register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noDeadLine, null));
        LectureSaveRequest pastDeadLine = LectureSaveRequest.builder().speaker("오성록").register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2020, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(pastDeadLine, null));
    }

    @DisplayName("강의 업데이트 테스트")
    @Test
    void updateTest() {
        LectureSaveRequest lectureSaveRequest = LectureSaveRequest.builder().speaker("오성록").register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        Lecture savedLecture = lectureService.save(lectureSaveRequest, null);
        String updateSpeaker = "홍길동";
        LectureUpdateRequest updateRequest = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        Lecture updatedLecture = lectureService.update(updateRequest, null);
        assertEquals(savedLecture.getId(), updatedLecture.getId());
        assertEquals(updateSpeaker, updatedLecture.getSpeaker());
    }

    @DisplayName("강의 업데이트 테스트 Request 검증")
    @Test
    void updateRequestValidate() {
        LectureSaveRequest lectureSaveRequest = LectureSaveRequest.builder().speaker("오성록").register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        Lecture savedLecture = lectureService.save(lectureSaveRequest, null);
        String updateSpeaker = "홍길동";
        LectureUpdateRequest noId = LectureUpdateRequest.updateRequestBuilder().speaker(updateSpeaker).register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noId, null));
        LectureUpdateRequest noSpeaker = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noSpeaker, null));
        LectureUpdateRequest noRegister = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noRegister, null));
        LectureUpdateRequest noTitle = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).register("이경희").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noTitle, null));
        LectureUpdateRequest noSubject = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).register("이경희").title("토비의 스프링 5").deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noSubject, null));
        LectureUpdateRequest noDeadLine = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noDeadLine, null));
        LectureUpdateRequest pastDeadLine = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2020, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(pastDeadLine, null));
    }

    @DisplayName("강의 삭제")
    @Test
    void deleteTest() {
        LectureSaveRequest lectureSaveRequest = LectureSaveRequest.builder().speaker("오성록").register("이경희").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        Lecture savedLecture = lectureService.save(lectureSaveRequest, null);
        Lecture getLecture = lectureService.findById(savedLecture.getId());
        assertEquals(savedLecture.getSpeaker(), getLecture.getSpeaker());
        assertEquals(savedLecture.getRegister(), getLecture.getRegister());

        lectureService.delete(savedLecture.getId());
        assertThrows(RuntimeException.class, () -> lectureService.findById(savedLecture.getId()));
    }

    @DisplayName("강의 조건별 가져오기")
    @Test
    void getLectureByRequest() {
        LectureSaveRequest lectureSaveRequest1 = LectureSaveRequest.builder().speaker("오성록").register("이경희").title("애플을 왜 좋은가?").subject(LectureSubject.FRONT).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureSaveRequest lectureSaveRequest2 = LectureSaveRequest.builder().speaker("토비").register("홍길동").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureSaveRequest lectureSaveRequest3 = LectureSaveRequest.builder().speaker("최범균").register("오성록").title("DDD에 대하여").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 5, 2)).build();
        LectureSaveRequest lectureSaveRequest4 = LectureSaveRequest.builder().speaker("조영호").register("강백호").title("오브젝트란 무엇인가").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 6, 26)).build();
        LectureSaveRequest lectureSaveRequest5 = LectureSaveRequest.builder().speaker("강타").register("채치수").title("가수가 되기 위해서").subject(LectureSubject.BACK).deadline(LocalDate.of(2022, 4, 1)).build();
        LectureSaveRequest lectureSaveRequest6 = LectureSaveRequest.builder().speaker("서태웅").register("서태웅").title("농구 잘하는 법").subject(LectureSubject.VUE).deadline(LocalDate.of(2022, 3, 23)).build();
        LectureSaveRequest lectureSaveRequest7 = LectureSaveRequest.builder().speaker("오성록").register("송태섭").title("노력은 재능이다").subject(LectureSubject.BACK).deadline(LocalDate.of(2022, 8, 12)).build();

        lectureService.save(lectureSaveRequest1, null);
        lectureService.save(lectureSaveRequest2, null);
        lectureService.save(lectureSaveRequest3, null);
        lectureService.save(lectureSaveRequest4, null);
        lectureService.save(lectureSaveRequest5, null);
        lectureService.save(lectureSaveRequest6, null);
        lectureService.save(lectureSaveRequest7, null);

        PageRequest pageRequest = PageRequest.of(0, 6);

        LectureGetRequest speaker오성록Request = LectureGetRequest.builder().speaker("오성록").build();
        Set<Lecture> speaker오성록Lecture = lectureService.getLectureByCondition(pageRequest, speaker오성록Request);
        assertEquals(2, speaker오성록Lecture.size());

        LectureGetRequest register오성록Request = LectureGetRequest.builder().register("오성록").build();
        Set<Lecture> register오성록Lecture = lectureService.getLectureByCondition(pageRequest, register오성록Request);
        assertEquals(1, register오성록Lecture.size());

        LectureGetRequest titleRequest = LectureGetRequest.builder().title("토비의 스프링 5").build();
        Set<Lecture> titleLecture = lectureService.getLectureByCondition(pageRequest, titleRequest);
        assertEquals(1, titleLecture.size());

        LectureGetRequest subjectJavaRequest = LectureGetRequest.builder().subject(LectureSubject.JAVA).build();
        Set<Lecture> subjectJavaLecture = lectureService.getLectureByCondition(pageRequest, subjectJavaRequest);
        assertEquals(3, subjectJavaLecture.size());

        LectureGetRequest noRequest = LectureGetRequest.builder().build();
        Set<Lecture> noConditionLecture = lectureService.getLectureByCondition(pageRequest, noRequest);
        assertEquals(6, noConditionLecture.size());
    }

}
