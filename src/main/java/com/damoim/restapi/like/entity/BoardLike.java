package com.damoim.restapi.like.entity;

import com.damoim.restapi.boards.entity.BoardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BoardLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Long id;

    private Long boardId;

    @JsonIgnore
    @OneToOne(mappedBy = "boardLike", fetch = FetchType.LAZY)
    private LikeStatus likeStatus;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Min(0)
    private int likeCount;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @LastModifiedBy
    private String modifier;
}
