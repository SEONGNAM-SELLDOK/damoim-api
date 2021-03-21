package com.damoim.restapi.like.entity;

import com.damoim.restapi.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LikeStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_status_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberLike;

    @JsonIgnore
    @OneToOne(mappedBy = "likeStatus", fetch = FetchType.LAZY)
    private BoardLike boardLike;

    @NotNull
    private Boolean status;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @LastModifiedBy
    private String modifier;
}
