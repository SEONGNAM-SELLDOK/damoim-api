package com.damoim.restapi.lecture.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Lecture Class
 * @author leekyunghee
 * @since 2021. 02. 25.
 */

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
    private long no;
    private long lectureId;
    private String title;
    private String description; // 상세 내용
    private String image;   // 썸네일 이미지
    private String speaker; // 발표자
    private String subject; // 주제
    private String route;   // 수업 경로
    private Date dueDate;   // 마감 기한

    @Column(updatable = false)
    private String register;
    @CreatedDate
    private LocalDateTime registeredDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    private String modifier;
}
