package com.damoim.restapi.member.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.damoim.restapi.boards.controller.SeminarController;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.boards.model.ModifyBoardsRequest;
import com.damoim.restapi.boards.model.SaveBoardRequest;
import com.damoim.restapi.boards.service.BoardService;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;
    @Autowired
    SeminarController seminarController;

    private long id;

    @BeforeEach
    public void setup() {
        Address address = new Address("KR", "seoul", "대왕판교로 1122 8층");
        Board board = Board.builder()
            .title("스프링 JPA 세미나")
            .content("스프링 JPA 세미나 내용 입니다.")
            .image("/img/0000.jpg")
            .address(address)
            .totalMember("20")
            .currentMember("5")
            .subject("JPA")
            .damoimTag(new DamoimTag("JPA"))
            .endDate(LocalDateTime.now())
            .build();
        ReadBoardsResponse response = boardService.save(board);
        id = response.getId();
    }

    @Test
    void save(){
        SaveBoardRequest request = SaveBoardRequest.builder()
            .title("스프링 JPA 세미나")
            .content("스프링 JPA 세미나 내용 입니다.")
            .image("/img/0000.jpg")
            .country("KR")
            .city("seoul")
            .street("대왕판교로 1122 8층")
            .totalMember("20")
            .currentMember("5")
            .subject("JPA")
            .damoimTag("JPA")
            .endDate(LocalDateTime.now())
            .build();

        ResponseEntity<ReadBoardsResponse> response = seminarController.saveSeminar(request);
    }

    @Test
    void findByIdTest() {
        Board boardId = boardService.findById(id);
        Assertions.assertTrue(boardId.getId() == id);
    }

    @Test
    void modifyTest() {
        ModifyBoardsRequest request = ModifyBoardsRequest.builder()
            .title("스프링 세미나 수정")
            .build();

        boardService.modify(id, request);
        Board boardId = boardService.findById(id);
        Assertions.assertTrue(boardId.getId() == id);
        Assertions.assertEquals(boardId.getTitle(), "스프링 세미나 수정");
    }

    @Test
    void deleteTest() {
        boardService.delete(id);
    }

}
