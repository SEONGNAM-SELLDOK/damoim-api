package com.damoim.restapi.member.service;

import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.SaveMemberRequest;
import com.damoim.restapi.seminar.comtroller.SeminarController;
import com.damoim.restapi.seminar.entity.Address;
import com.damoim.restapi.seminar.entity.DamoimTag;
import com.damoim.restapi.seminar.entity.Seminar;
import com.damoim.restapi.seminar.model.ModifySeminarRequest;
import com.damoim.restapi.seminar.model.SaveSeminarRequest;
import com.damoim.restapi.seminar.service.SeminarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@SpringBootTest
@Transactional
public class SeminarServiceTest {

    @Autowired
    SeminarController seminarController;
    @Autowired
    SeminarService seminarService;

    private long id;

    @BeforeEach
    public void setup(){
        Address address = new Address("KR", "seoul", "대왕판교로 1122 8층");
        Seminar seminar = Seminar.builder()
                .title("스프링 JPA 세미나")
                .content("스프링 JPA 세미나 내용 입니다.")
                .image("/img/0000.jpg")
                .address(address)
                .totalMember("20")
                .currentMember("5")
                .subject("JPA")
                .damoimTag(DamoimTag.JPA)
                .endDate(LocalDateTime.now())
                .build();
        id = seminarService.save(seminar);
    }

    @Test
    void save(){
        SaveSeminarRequest request = SaveSeminarRequest.builder()
                .title("스프링 JPA 세미나")
                .content("스프링 JPA 세미나 내용 입니다.")
                .image("/img/0000.jpg")
                .country("KR")
                .city("seoul")
                .street("대왕판교로 1122 8층")
                .totalMember("20")
                .currentMember("5")
                .subject("JPA")
                .damoimTag(DamoimTag.JPA)
                .endDate(LocalDateTime.now())
                .build();

        ResponseEntity<String> id = seminarController.save(request);
    }

    @Test
    void findByIdTest() {
        Optional<Seminar> seminar = seminarService.findById(id);
        Assertions.assertTrue(seminar.isPresent());
    }

    @Test
    void modifyTest() {
        ModifySeminarRequest request = ModifySeminarRequest.builder()
                .title("스프링 세미나 수정")
                .build();

        seminarService.modify(id, request);
        Optional<Seminar> seminar = seminarService.findById(id);
        Assertions.assertTrue(seminar.isPresent());
        Assertions.assertEquals(seminar.get().getTitle(), "스프링 세미나 수정");
    }

    @Test
    void deleteTest() {
        seminarService.delete(id);

        Optional<Seminar> seminar = seminarService.findById(id);
        Assertions.assertTrue(seminar.isEmpty());
    }

}
