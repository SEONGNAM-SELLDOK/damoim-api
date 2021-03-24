package com.damoim.restapi.recruit.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Recruit {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @CreatedBy
    private String register;
    private String company;
    private String title;
    private String description;
    private String location;

    @Enumerated(EnumType.STRING)
    private RecruitTag tag;
    private Integer reward;

    private String image;

    private LocalDate deadline;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @LastModifiedBy
    private String modifier;

    public boolean isRegister(String register) {
        return this.register.equals(register);
    }
}
