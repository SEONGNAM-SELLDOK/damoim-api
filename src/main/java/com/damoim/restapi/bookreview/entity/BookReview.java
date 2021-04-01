package com.damoim.restapi.bookreview.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@EqualsAndHashCode(of = "id")
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class BookReview {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String title;
    private String description;
    private String image;
    private String isbn;
    private String publisher;
    private String writer;
    private String subject;
    @ElementCollection
    private Set<String> tag;
    private LocalDate deadline;

    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedBy
    private String register;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    @LastModifiedBy
    private String modifier;

    public boolean isRegister(String register) {
        if (Objects.isNull(register)) {
            throw new RuntimeException();
        }
        return this.register.equals(register);
    }

    public void update(BookReview origin) {
        if (Objects.isNull(origin) || Objects.isNull(origin.getId()) || Objects.isNull(id) || !id.equals(origin.getId())) {
            throw new RuntimeException();
        }

//        this.title = updateBookReview.title;
//        this.description = updateBookReview.description;
//        this.image = updateBookReview.image;
//        this.isbn = updateBookReview.isbn;
//        this.publisher = updateBookReview.publisher;
//        this.writer = updateBookReview.writer;
//        this.subject = updateBookReview.subject;
//        this.tag = updateBookReview.tag;
//        this.deadline = updateBookReview.deadline;
        this.register = origin.register;
        this.createdDate = origin.createdDate;
    }
}
