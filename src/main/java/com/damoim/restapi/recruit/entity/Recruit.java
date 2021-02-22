package com.damoim.restapi.recruit.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recruit {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String register;
    private String company;
    private String title;
    private String description;

    @ElementCollection
    private List<String> likes;

    @ElementCollection
    private List<String> bookMarks;

    private LocalDate deadline;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
