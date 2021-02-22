package com.damoim.restapi.seminar.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReadSeminarResponse {
    private String title;
    private String content;
    private String image;
    private String country;
    private String city;
    private String street;
    private String totalMember;
    private String currentMember;
    private String subject;
    private LocalDateTime endDate;

}
