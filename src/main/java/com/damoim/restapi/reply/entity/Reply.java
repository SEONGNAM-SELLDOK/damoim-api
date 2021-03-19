package com.damoim.restapi.reply.entity;


import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.reply.service.ReplyClosedException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "no")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private Long boardId;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private String writer;

    @Lob
    private String content;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentReply")
    @JsonManagedReference
    private List<ChildReply> childReply = new ArrayList<>();

    @Builder.Default
    private Boolean closed = false;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    public void updateContent(String content) {
        this.content = content;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void closed() {
        this.content = "삭제된 댓글입니다.";
        this.closed = true;
    }

    public void checkClosed() {
        if (Boolean.TRUE.equals(closed)) {
            throw new ReplyClosedException(this.no);
        }
    }

    public boolean childListIsEmpty() {
        return this.childReply.isEmpty();
    }

}
