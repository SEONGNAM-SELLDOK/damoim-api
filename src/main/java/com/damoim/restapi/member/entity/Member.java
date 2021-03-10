package com.damoim.restapi.member.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Member
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    private String id;
    private String name;
    private String pwd;
    private String profilePicUrl;
    private String email;
    @Column(updatable = false)
    private String register;
    @CreatedDate
    private LocalDateTime registeredDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    private String modifier;
}
