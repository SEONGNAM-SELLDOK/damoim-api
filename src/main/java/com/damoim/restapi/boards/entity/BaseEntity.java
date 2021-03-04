package com.damoim.restapi.boards.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author gisung go
 * @since 2021-02-22
 */
@MappedSuperclass
public class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime createDate; // 등록일시
    private LocalDateTime updateDate; // 수정일시

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
