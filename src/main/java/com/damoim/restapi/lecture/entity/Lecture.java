package com.damoim.restapi.lecture.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Lecture Class
 *
 * @author leekyunghee
 * @since 2021. 02. 25.
 */

@EqualsAndHashCode(of = "id")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;       // 상세 내용
    private String image;             // 썸네일 이미지
    private String speaker;           // 발표자
    @Enumerated(EnumType.STRING)
    private LectureSubject subject;  // 주제
    private String route;             // 수업 경로
    private LocalDate deadline;   // 마감 기한

    @CreatedBy
    private String register;
    @CreatedDate
    private LocalDateTime registeredDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    @LastModifiedBy
    private String modifier;

    public boolean isRegister(String register) {
        if (Objects.isNull(register)) {
            throw new IllegalArgumentException();
        }
        return this.register.equals(register);
    }

    public void update(Lecture updateLecture) {
        if (Objects.isNull(updateLecture) || Objects.isNull(id) || Objects.isNull(updateLecture.getId()) || !id.equals(updateLecture.getId())) {
            throw new IllegalArgumentException();
        }
        this.title = updateLecture.title;
        this.description = updateLecture.description;
        this.image = updateLecture.image;
        this.speaker = updateLecture.speaker;
        this.subject = updateLecture.subject;
        this.route = updateLecture.route;
        this.deadline = updateLecture.deadline;
    }
}
