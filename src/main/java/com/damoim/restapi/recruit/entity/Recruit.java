package com.damoim.restapi.recruit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Recruit {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String register;
    private String company;
    private String title;
    private String description;
    private String location;
    private String[] tags;
    private Integer reward;

    private LocalDate deadline;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Recruit)) {
            return false;
        }
        Recruit other = (Recruit) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
