package com.damoim.restapi.lecture.service;

import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.entity.LectureSubject;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

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

}
