package com.damoim.restapi.member.service;

import com.damoim.restapi.boards.controller.BoardsController;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Boards;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.boards.model.ModifyBoardsRequest;
import com.damoim.restapi.boards.model.SaveBoardRequest;
import com.damoim.restapi.boards.service.BoardsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * @author gisung go
 * @since 2021-02-22
 * */
@SpringBootTest
@Transactional
public class BoardsServiceTest {

    //    @Autowired
    //    BoardsController boardsController;
    @Autowired
    BoardsService boardsService;

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
        id = boardsService.save(boards);
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

        //        ResponseEntity<String> id = boardsController.save(request, (BoardType) seminar);
    }

    @Test
    void findByIdTest() {
        Optional<Boards> seminar = boardsService.findById(id);
        Assertions.assertTrue(seminar.isPresent());
    }

    @Test
    void modifyTest() {
        ModifyBoardsRequest request = ModifyBoardsRequest.builder()
            .title("스프링 세미나 수정")
            .build();

        boardsService.modify(id, request);
        Optional<Boards> seminar = boardsService.findById(id);
        Assertions.assertTrue(seminar.isPresent());
        Assertions.assertEquals(seminar.get().getTitle(), "스프링 세미나 수정");
    }

    @Test
    void deleteTest() {
        boardsService.delete(id);

        Optional<Boards> seminar = boardsService.findById(id);
        Assertions.assertTrue(seminar.isEmpty());
    }

}
