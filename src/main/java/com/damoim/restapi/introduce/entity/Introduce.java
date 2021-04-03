package com.damoim.restapi.introduce.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Introduce {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String content;

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
}
