package com.damoim.restapi.boards.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
public class Boards extends BaseEntity {
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

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;
}
