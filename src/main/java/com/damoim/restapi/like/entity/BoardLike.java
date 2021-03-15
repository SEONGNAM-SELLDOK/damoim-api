package com.damoim.restapi.like.entity;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.member.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberLike;

    private String PageId;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Min(0)
    private int boardCount;

    @CreatedDate
    private LocalDateTime createDate; // 등록일시

    @LastModifiedDate
    private LocalDateTime updateDate; // 수정일시

    @LastModifiedBy
    private String modifier;
}
