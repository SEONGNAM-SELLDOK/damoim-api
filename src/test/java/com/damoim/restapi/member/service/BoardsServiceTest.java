package com.damoim.restapi.member.service;

import com.damoim.restapi.boards.controller.SeminarController;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Boards;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.boards.model.ModifyBoardsRequest;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.damoim.restapi.boards.model.SaveBoardRequest;
import com.damoim.restapi.boards.service.BoardsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@SpringBootTest
@Transactional
public class BoardsServiceTest {

    @Autowired
    BoardsService boardsService;
    @Autowired
    SeminarController seminarController;

    private long id;

    @BeforeEach
    public void setup(){
        Address address = new Address("KR", "seoul", "대왕판교로 1122 8층");
        Boards boards = Boards.builder()
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
        ReadBoardsResponse response = boardsService.save(boards);
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
        Boards boardId = boardsService.findById(id);
        Assertions.assertTrue(boardId.getId() == id);
    }

    @Test
    void modifyTest() {
        ModifyBoardsRequest request = ModifyBoardsRequest.builder()
            .title("스프링 세미나 수정")
            .build();

        boardsService.modify(id, request);
        Boards boardId = boardsService.findById(id);
        Assertions.assertTrue(boardId.getId() == id);
        Assertions.assertEquals(boardId.getTitle(), "스프링 세미나 수정");
    }

    @Test
    void deleteTest() {
        boardsService.delete(id);
    }

}
