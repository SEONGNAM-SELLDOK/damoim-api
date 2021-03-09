package com.damoim.restapi.boards.entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author gjsung.Go
 * @since 2021.02.20
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String image;
    @Embedded
    private Address address;
    private String totalMember;
    private String currentMember;
    private String subject;

    @Enumerated(EnumType.STRING)
    private DamoimTag damoimTag;

    @NotBlank
    private String boardLike;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

}
