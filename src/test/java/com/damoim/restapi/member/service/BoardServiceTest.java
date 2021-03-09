package com.damoim.restapi.member.service;

import com.damoim.restapi.boards.dao.BoardRepository;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.boards.model.SaveBoardRequest;
import com.damoim.restapi.boards.service.BoardService;
import com.damoim.restapi.config.DamoimFileUtil;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gisung go
 * @since 2021-02-22
 */
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    DamoimFileUtil damoimFileUtil;

    private long id;

    @BeforeEach
    public void setup() {
        Address address = new Address("KR", "seoul", "대왕판교로 1122 8층");
        Board board = Board.builder()
            .title("스프링 JPA 세미나")
            .content("스프링 JPA 세미나 내용 입니다.")
            .address(address)
            .totalMember("20")
            .currentMember("5")
            .subject("JPA")
            .damoimTag(new DamoimTag("JPA"))
            .endDate(LocalDateTime.now())
            .build();
    }

    @Test
    void save(){
        SaveBoardRequest request = SaveBoardRequest.builder()
            .title("스프링 JPA 세미나")
            .content("스프링 JPA 세미나 내용 입니다.")
            .country("KR")
            .city("seoul")
            .street("대왕판교로 1122 8층")
            .totalMember("20")
            .currentMember("5")
            .subject("JPA")
            .damoimTag("JPA")
            .endDate(LocalDateTime.now())
            .build();
    }


}
