package com.damoim.restapi.boards.entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.damoim.restapi.like.entity.BoardLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;

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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

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

    @OneToOne
    @JoinColumn(name = "board_like_id")
    private BoardLike boardLike;

    @LastModifiedBy
    private String modifier;

}
