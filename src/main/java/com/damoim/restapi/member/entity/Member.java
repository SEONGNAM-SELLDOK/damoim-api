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

    // 하나의 맴버는 여러개의 like를 가질 수 있다.
    @JsonIgnore
    @OneToMany(mappedBy = "boardLike", cascade = CascadeType.ALL)
    private List<BoardLike> boardLikeLists = new ArrayList<>();

    // 양방향 연관관계
    public void  addBoardLike(BoardLike boardLike) {
        boardLikeLists.add(boardLike);
        boardLike.setMember(this);
    }

}
