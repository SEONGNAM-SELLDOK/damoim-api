package com.damoim.restapi.boards.model;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Getter
@Setter
@Builder
public class SaveBoardRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String image;
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String totalMember;
    @NotNull
    private String currentMember;
    @NotNull
    private String subject;
    @NotNull
    private String damoimTag;
    @NotNull
    private LocalDateTime endDate;
}
