package com.damoim.restapi.lecture.service;

import com.damoim.restapi.lecture.entity.LectureSubject;
import com.damoim.restapi.lecture.model.LectureGetRequest;
import com.damoim.restapi.lecture.model.LectureResponse;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
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
class LectureServiceTest {

    @Autowired
    LectureService lectureService;

    @DisplayName("강의 추천 저장 테스트")
    @Test
    void saveTest() {
        LectureSaveRequest lectureSaveRequest = LectureSaveRequest.builder().speaker("오성록").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureResponse lecture = lectureService.save(lectureSaveRequest, null);
        assertEquals(lectureSaveRequest.getTitle(), lecture.getTitle());
    }

    @DisplayName("강의 저장 테스트 Request 검증")
    @Test
    void saveRequestValidate() {
        LectureSaveRequest noSpeaker = LectureSaveRequest.builder().title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noSpeaker, null));
        LectureSaveRequest noTitle = LectureSaveRequest.builder().speaker("오성록").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noTitle, null));
        LectureSaveRequest noSubject = LectureSaveRequest.builder().speaker("오성록").title("토비의 스프링 5").deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noSubject, null));
        LectureSaveRequest noDeadLine = LectureSaveRequest.builder().speaker("오성록").title("토비의 스프링 5").subject(LectureSubject.JAVA).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(noDeadLine, null));
        LectureSaveRequest pastDeadLine = LectureSaveRequest.builder().speaker("오성록").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2020, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.save(pastDeadLine, null));
    }

    @DisplayName("강의 업데이트 테스트")
    @WithAccount("lokie")
    @Test
    void updateTest() {
        LectureSaveRequest lectureSaveRequest = LectureSaveRequest.builder().speaker("오성록").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureResponse savedLecture = lectureService.save(lectureSaveRequest, null);
        String updateSpeaker = "홍길동";
        LectureUpdateRequest updateRequest = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureResponse updatedLecture = lectureService.update(updateRequest, null, AuthUser.builder().email("lokie").build());
        assertEquals(savedLecture.getId(), updatedLecture.getId());
        assertEquals(updateSpeaker, updatedLecture.getSpeaker());
    }

    @DisplayName("강의 업데이트 테스트 Request 검증")
    @WithAccount("lokie")
    @Test
    void updateRequestValidate() {
        LectureSaveRequest lectureSaveRequest = LectureSaveRequest.builder().speaker("오성록").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureResponse savedLecture = lectureService.save(lectureSaveRequest, null);
        String updateSpeaker = "홍길동";
        LectureUpdateRequest noId = LectureUpdateRequest.updateRequestBuilder().speaker(updateSpeaker).title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noId, null, AuthUser.builder().email("lokie").build()));
        LectureUpdateRequest noSpeaker = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noSpeaker, null, AuthUser.builder().email("lokie").build()));
        LectureUpdateRequest noTitle = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noTitle, null, AuthUser.builder().email("lokie").build()));
        LectureUpdateRequest noSubject = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).title("토비의 스프링 5").deadline(LocalDate.of(2022, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noSubject, null, AuthUser.builder().email("lokie").build()));
        LectureUpdateRequest noDeadLine = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).title("토비의 스프링 5").subject(LectureSubject.JAVA).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(noDeadLine, null, AuthUser.builder().email("lokie").build()));
        LectureUpdateRequest pastDeadLine = LectureUpdateRequest.updateRequestBuilder().id(savedLecture.getId()).speaker(updateSpeaker).title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2020, 2, 2)).build();
        assertThrows(RuntimeException.class, () -> lectureService.update(pastDeadLine, null, AuthUser.builder().email("lokie").build()));
    }

    @DisplayName("강의 삭제")
    @WithAccount("lokie")
    @Test
    void deleteTest() {
        LectureSaveRequest lectureSaveRequest = LectureSaveRequest.builder().speaker("오성록").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureResponse savedLecture = lectureService.save(lectureSaveRequest, null);
        final Long savedLectureId = savedLecture.getId();
        LectureResponse getLecture = lectureService.findById(savedLectureId);
        assertEquals(savedLecture.getSpeaker(), getLecture.getSpeaker());
        assertEquals(savedLecture.getRegister(), getLecture.getRegister());

        lectureService.delete(savedLectureId, AuthUser.builder().email("lokie").build());
        assertThrows(RuntimeException.class, () -> lectureService.findById(savedLectureId));
    }

    @DisplayName("강의 조건별 가져오기")
    @Test
    void getLectureByRequest() {
        LectureSaveRequest lectureSaveRequest1 = LectureSaveRequest.builder().speaker("오성록").title("애플을 왜 좋은가?").subject(LectureSubject.FRONT).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureSaveRequest lectureSaveRequest2 = LectureSaveRequest.builder().speaker("토비").title("토비의 스프링 5").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 2, 2)).build();
        LectureSaveRequest lectureSaveRequest3 = LectureSaveRequest.builder().speaker("최범균").title("DDD에 대하여").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 5, 2)).build();
        LectureSaveRequest lectureSaveRequest4 = LectureSaveRequest.builder().speaker("조영호").title("오브젝트란 무엇인가").subject(LectureSubject.JAVA).deadline(LocalDate.of(2022, 6, 26)).build();
        LectureSaveRequest lectureSaveRequest5 = LectureSaveRequest.builder().speaker("강타").title("가수가 되기 위해서").subject(LectureSubject.BACK).deadline(LocalDate.of(2022, 4, 1)).build();
        LectureSaveRequest lectureSaveRequest6 = LectureSaveRequest.builder().speaker("서태웅").title("농구 잘하는 법").subject(LectureSubject.VUE).deadline(LocalDate.of(2022, 3, 23)).build();
        LectureSaveRequest lectureSaveRequest7 = LectureSaveRequest.builder().speaker("오성록").title("노력은 재능이다").subject(LectureSubject.BACK).deadline(LocalDate.of(2022, 8, 12)).build();

        lectureService.save(lectureSaveRequest1, null);
        lectureService.save(lectureSaveRequest2, null);
        lectureService.save(lectureSaveRequest3, null);
        lectureService.save(lectureSaveRequest4, null);
        lectureService.save(lectureSaveRequest5, null);
        lectureService.save(lectureSaveRequest6, null);
        lectureService.save(lectureSaveRequest7, null);

        PageRequest pageRequest = PageRequest.of(0, 6);

        LectureGetRequest speaker오성록Request = LectureGetRequest.builder().speaker("오성록").build();
        Set<LectureResponse> speaker오성록Lecture = lectureService.getLectureByCondition(pageRequest, speaker오성록Request);
        assertEquals(2, speaker오성록Lecture.size());

        LectureGetRequest titleRequest = LectureGetRequest.builder().title("토비의 스프링 5").build();
        Set<LectureResponse> titleLecture = lectureService.getLectureByCondition(pageRequest, titleRequest);
        assertEquals(1, titleLecture.size());

        LectureGetRequest subjectJavaRequest = LectureGetRequest.builder().subject(LectureSubject.JAVA).build();
        Set<LectureResponse> subjectJavaLecture = lectureService.getLectureByCondition(pageRequest, subjectJavaRequest);
        assertEquals(3, subjectJavaLecture.size());

        LectureGetRequest noRequest = LectureGetRequest.builder().build();
        Set<LectureResponse> noConditionLecture = lectureService.getLectureByCondition(pageRequest, noRequest);
        assertEquals(6, noConditionLecture.size());

        LectureGetRequest deadlineFromRequest = LectureGetRequest.builder().deadLineFrom(LocalDate.of(2022, 8, 1)).build();
        Set<LectureResponse> deadlineFromLecture = lectureService.getLectureByCondition(pageRequest, deadlineFromRequest);
        assertEquals(1, deadlineFromLecture.size());

        LectureGetRequest deadlineToRequest = LectureGetRequest.builder().deadLineTo(LocalDate.of(2022, 5, 1)).build();
        Set<LectureResponse> deadlineToLecture = lectureService.getLectureByCondition(pageRequest, deadlineToRequest);
        assertEquals(4, deadlineToLecture.size());

        LectureGetRequest deadlineFromToRequest = LectureGetRequest.builder().deadLineTo(LocalDate.of(2022, 8, 1)).deadLineFrom(LocalDate.of(2022, 5, 1)).build();
        Set<LectureResponse> deadlineFromToLecture = lectureService.getLectureByCondition(pageRequest, deadlineFromToRequest);
        assertEquals(2, deadlineFromToLecture.size());
    }

}
