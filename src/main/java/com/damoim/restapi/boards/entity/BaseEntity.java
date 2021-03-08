package com.damoim.restapi.boards.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createDate; // 등록일시
    @LastModifiedDate
    private LocalDateTime updateDate; // 수정일시
}
