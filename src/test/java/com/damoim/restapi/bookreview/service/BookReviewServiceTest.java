package com.damoim.restapi.bookreview.service;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.bookreview.model.*;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.reply.model.request.RequestSaveReply;
import com.damoim.restapi.reply.model.response.ResponseReply;
import com.damoim.restapi.reply.service.ReplyService;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@DisplayName("도서 리뷰 서비스 테스트")
@Transactional
@SpringBootTest
class BookReviewServiceTest {

    @Autowired
    BookReviewService bookReviewService;

    @Autowired
    ReplyService replyService;

    @DisplayName("도서 리뷰 저장 테스트")
    @WithAccount("lokie")
    @Test
    void createTest() {
        BookReviewSaveRequest saveRequest = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        BookReviewResponse savedBookReview = bookReviewService.save(saveRequest, null);
        assertEquals(saveRequest.getTitle(), savedBookReview.getTitle());
        assertEquals(saveRequest.getDescription(), savedBookReview.getDescription());
        assertEquals(saveRequest.getIsbn(), savedBookReview.getIsbn());
        assertEquals("lokie", savedBookReview.getRegister());
    }

    @DisplayName("도서 리뷰 저장 검증 테스트")
    @Test
    void createValidateTest() {
        BookReviewSaveRequest noTitle = BookReviewSaveRequest.builder().description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.save(noTitle, null));
        BookReviewSaveRequest noIsbn = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.save(noIsbn, null));
        BookReviewSaveRequest noPublisher = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.save(noPublisher, null));
        BookReviewSaveRequest noWriter = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.save(noWriter, null));
        BookReviewSaveRequest noSubject = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.save(noSubject, null));
        BookReviewSaveRequest noDeadline = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.save(noDeadline, null));
        BookReviewSaveRequest pastDeadline = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2020, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.save(pastDeadline, null));
    }

    @DisplayName("도서 리뷰 가져오기 테스트")
    @Test
    void getAllTest() {
        BookReviewSaveRequest saveRequest = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        bookReviewService.save(saveRequest, null);
        bookReviewService.save(saveRequest, null);
        PageRequest pageRequest = PageRequest.of(0, 6);

        assertEquals(2, bookReviewService.getAll(pageRequest).size());
        bookReviewService.save(saveRequest, null);
        assertEquals(3, bookReviewService.getAll(pageRequest).size());
    }

    @DisplayName("도서 리뷰 업데이트 테스트")
    @WithAccount("lokie")
    @Test
    void updateTest() {
        BookReviewSaveRequest saveRequest = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        BookReviewResponse savedBookReview = bookReviewService.save(saveRequest, null);
        String updateTitle = "(수정)객체지향의 사실과 오해를 읽고...";
        BookReviewUpdateRequest updateRequest = BookReviewUpdateRequest.updateBookReviewBuilder().id(savedBookReview.getId()).title(updateTitle).description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        BookReviewResponse updatedBookReview = bookReviewService.update(updateRequest, null, AuthUser.builder().email("lokie").build());
        assertEquals(updateTitle, updatedBookReview.getTitle());
    }

    @DisplayName("도서 리뷰 업데이트 검증 테스트")
    @WithAccount("lokie")
    @Test
    void updateValidateTest() {
        AuthUser authUser = AuthUser.builder().email("lokie").build();
        BookReviewUpdateRequest noId = BookReviewUpdateRequest.updateBookReviewBuilder().description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.update(noId, null, authUser));
        BookReviewUpdateRequest noTitle = BookReviewUpdateRequest.updateBookReviewBuilder().id(1).description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.update(noTitle, null, authUser));
        BookReviewUpdateRequest noIsbn = BookReviewUpdateRequest.updateBookReviewBuilder().id(1).title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.update(noIsbn, null, authUser));
        BookReviewUpdateRequest noPublisher = BookReviewUpdateRequest.updateBookReviewBuilder().id(1).title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.update(noPublisher, null, authUser));
        BookReviewUpdateRequest noWriter = BookReviewUpdateRequest.updateBookReviewBuilder().id(1).title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.update(noWriter, null, authUser));
        BookReviewUpdateRequest noSubject = BookReviewUpdateRequest.updateBookReviewBuilder().id(1).title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.update(noSubject, null, authUser));
        BookReviewUpdateRequest noDeadline = BookReviewUpdateRequest.updateBookReviewBuilder().id(1).title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.update(noDeadline, null, authUser));
        BookReviewUpdateRequest pastDeadline = BookReviewUpdateRequest.updateBookReviewBuilder().id(1).title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2020, 3, 16)).build();
        assertThrows(RuntimeException.class, () -> bookReviewService.update(pastDeadline, null, authUser));
    }

    @DisplayName("도서 리뷰 삭제 테스트")
    @WithAccount("lokie")
    @Test
    void deleteTest() {
        BookReviewSaveRequest saveRequest = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP"))).deadline(LocalDate.of(2022, 3, 16)).build();
        BookReviewResponse savedBookReview = bookReviewService.save(saveRequest, null);
        PageRequest pageRequest = PageRequest.of(0, 6);
        assertEquals(1, bookReviewService.getAll(pageRequest).size());
        AuthUser authUser = AuthUser.builder().email("lokie").build();
        bookReviewService.delete(savedBookReview.getId(), authUser);
        assertEquals(0, bookReviewService.getAll(pageRequest).size());
        assertThrows(RuntimeException.class, () -> bookReviewService.delete(50105012301240L, authUser));
    }

    @DisplayName("도서 리뷰 조건 가져오기 테스트")
    @Test
    void getByConditionTest() {
        BookReviewSaveRequest oop1 = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("어려운 내용").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP", "솔리드"))).deadline(LocalDate.of(2023, 3, 16)).build();
        BookReviewSaveRequest oop2 = BookReviewSaveRequest.builder().title("조영호님의 책을 읽고...").description("아주 좋은 내용이다").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP", "SOLID"))).deadline(LocalDate.of(2022, 3, 7)).build();
        BookReviewSaveRequest oop3 = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("꼭 읽으세요!").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP", "객체지향"))).deadline(LocalDate.of(2022, 5, 16)).build();
        BookReviewSaveRequest toby1 = BookReviewSaveRequest.builder().title("토비의 스프링 3.1 을 읽고...").description("아주 좋은 내용이다").isbn("978-89-6077-103-1").publisher("에이콘").writer("토비").subject("스프링").tag(new HashSet<>(Arrays.asList("토비", "스프링"))).deadline(LocalDate.of(2022, 4, 11)).build();
        BookReviewSaveRequest toby2 = BookReviewSaveRequest.builder().title("토비의 스프링 이란").description("very good").isbn("978-89-6077-103-1").publisher("에이콘").writer("토비").subject("스프링").tag(new HashSet<>(Arrays.asList("스프링", "객체"))).deadline(LocalDate.of(2022, 2, 17)).build();
        BookReviewSaveRequest toby3 = BookReviewSaveRequest.builder().title("토비의 스프링은").description("꼭 읽으세요!").isbn("978-89-6077-103-1").publisher("에이콘").writer("토비").subject("스프링").tag(new HashSet<>(Arrays.asList("스프링", "IOC"))).deadline(LocalDate.of(2022, 5, 2)).build();
        BookReviewSaveRequest toby4 = BookReviewSaveRequest.builder().title("토비의 스프링을 읽어라!").description("아주 좋은 내용이다").isbn("978-89-6077-103-1").publisher("에이콘").writer("토비").subject("스프링").tag(new HashSet<>(Arrays.asList("스프링", "DI"))).deadline(LocalDate.of(2022, 3, 13)).build();
        BookReviewSaveRequest object = BookReviewSaveRequest.builder().title("오브젝트 를 읽고").description("뜻깊은 메시지가 많다.").isbn("979-11-5839-140-9").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("OOP", "객체"))).deadline(LocalDate.of(2022, 3, 27)).build();
        bookReviewService.save(oop1, null);
        bookReviewService.save(oop2, null);
        bookReviewService.save(oop3, null);
        bookReviewService.save(toby1, null);
        bookReviewService.save(toby2, null);
        bookReviewService.save(toby3, null);
        bookReviewService.save(toby4, null);
        bookReviewService.save(object, null);
        PageRequest pageRequest = PageRequest.of(0, 6);

        BookReviewGetRequest oopTitleRequest = BookReviewGetRequest.builder().title("객체지향의 사실과 오해를 읽고...").build();
        Set<BookReviewResponse> oopTitleBookReviewSet = bookReviewService.getByCondition(oopTitleRequest, pageRequest);
        assertEquals(2, oopTitleBookReviewSet.size());

        BookReviewGetRequest descriptionRequest = BookReviewGetRequest.builder().description("내용").build();
        Set<BookReviewResponse> descriptionBookReviewSet = bookReviewService.getByCondition(descriptionRequest, pageRequest);
        assertEquals(4, descriptionBookReviewSet.size());

        BookReviewGetRequest isbnRequest = BookReviewGetRequest.builder().isbn("978-89-98139-76-6").build();
        Set<BookReviewResponse> isbnBookReviewSet = bookReviewService.getByCondition(isbnRequest, pageRequest);
        assertEquals(3, isbnBookReviewSet.size());

        BookReviewGetRequest publisherRequest = BookReviewGetRequest.builder().publisher("에이콘").build();
        Set<BookReviewResponse> publisherBookReviewSet = bookReviewService.getByCondition(publisherRequest, pageRequest);
        assertEquals(4, publisherBookReviewSet.size());

        BookReviewGetRequest writerRequest = BookReviewGetRequest.builder().writer("조영호").build();
        Set<BookReviewResponse> writerBookReviewSet = bookReviewService.getByCondition(writerRequest, pageRequest);
        assertEquals(4, writerBookReviewSet.size());

        BookReviewGetRequest subjectRequest = BookReviewGetRequest.builder().subject("스프링").build();
        Set<BookReviewResponse> subjectBookReviewSet = bookReviewService.getByCondition(subjectRequest, pageRequest);
        assertEquals(4, subjectBookReviewSet.size());

        BookReviewGetRequest tagRequest = BookReviewGetRequest.builder().tag("객체").build();
        Set<BookReviewResponse> tagBookReviewSet = bookReviewService.getByCondition(tagRequest, pageRequest);
        assertEquals(5, tagBookReviewSet.size());

        BookReviewGetRequest fromDeadLineRequest = BookReviewGetRequest.builder().deadlineFrom(LocalDate.of(2022, 5, 1)).build();
        Set<BookReviewResponse> fromDeadLineBookReviewSet = bookReviewService.getByCondition(fromDeadLineRequest, pageRequest);
        assertEquals(3, fromDeadLineBookReviewSet.size());

        BookReviewGetRequest toDeadLineRequest = BookReviewGetRequest.builder().deadlineTo(LocalDate.of(2022, 5, 1)).build();
        Set<BookReviewResponse> toDeadLineBookReviewSet = bookReviewService.getByCondition(toDeadLineRequest, pageRequest);
        assertEquals(5, toDeadLineBookReviewSet.size());

        BookReviewGetRequest fromToDeadLineRequest = BookReviewGetRequest.builder().deadlineFrom(LocalDate.of(2022, 3, 1)).deadlineTo(LocalDate.of(2022, 5, 1)).build();
        Set<BookReviewResponse> fromToDeadLineBookReviewSet = bookReviewService.getByCondition(fromToDeadLineRequest, pageRequest);
        assertEquals(4, fromToDeadLineBookReviewSet.size());

//        BookReviewGetRequest registerRequest = BookReviewGetRequest.builder().register("오성록").build();
//        Set<BookReview> registerBookReviewSet = bookReviewService.getByCondition(registerRequest, pageRequest);
//        assertEquals(3, registerBookReviewSet.size());

    }

    @DisplayName("도서리뷰 댓글 입력하기")
    @Test
    void createBookReviewReplyTest() {
        BookReviewSaveRequest bookReviewSaveRequest = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("어려운 내용").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP", "솔리드"))).deadline(LocalDate.of(2023, 3, 16)).build();
        BookReviewResponse bookReviewResponse = bookReviewService.save(bookReviewSaveRequest, null);
        RequestSaveReply requestSaveReply = new RequestSaveReply();
        requestSaveReply.setIsChildId(false);
        requestSaveReply.setWriter("오성록");
        requestSaveReply.setContent("댓글댓글");
        RequestSaveReply saveReply = requestSaveReply.checkUrl("bookreview");
        ResponseReply responseReply = replyService.replySave(bookReviewResponse.getId(), saveReply);
        assertEquals("오성록", responseReply.getReply().getWriter());
    }

    @DisplayName("도서리뷰 댓글 가져오기")
    @Test
    void getBookReviewReplyTest() {
        BookReviewSaveRequest bookReviewSaveRequest = BookReviewSaveRequest.builder().title("객체지향의 사실과 오해를 읽고...").description("어려운 내용").isbn("978-89-98139-76-6").publisher("위키북스").writer("조영호").subject("객체지향").tag(new HashSet<>(Arrays.asList("객체", "OOP", "솔리드"))).deadline(LocalDate.of(2023, 3, 16)).build();
        BookReviewResponse bookReviewResponse = bookReviewService.save(bookReviewSaveRequest, null);
        RequestSaveReply requestSaveReply = new RequestSaveReply();
        requestSaveReply.setIsChildId(false);
        requestSaveReply.setWriter("오성록");
        requestSaveReply.setContent("댓글댓글");
        RequestSaveReply saveReply = requestSaveReply.checkUrl("bookreview");
        replyService.replySave(bookReviewResponse.getId(), saveReply);

        RequestSaveReply requestSaveReply2 = new RequestSaveReply();
        requestSaveReply2.setIsChildId(false);
        requestSaveReply2.setWriter("홍길동");
        requestSaveReply2.setContent("222");
        RequestSaveReply saveReply2 = requestSaveReply2.checkUrl("bookreview");
        replyService.replySave(bookReviewResponse.getId(), saveReply2);

        BookReviewResponseWithReply reviewResponseWithReply = bookReviewService.getBookReviewIncludeReply(bookReviewResponse.getId(), BoardType.BOOKREVIEW);
        assertEquals(2, reviewResponseWithReply.getReplyList().size());
        assertEquals("222", reviewResponseWithReply.getReplyList().get(1).getContent());
    }
}
