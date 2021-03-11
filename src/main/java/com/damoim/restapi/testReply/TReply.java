package com.damoim.restapi.testReply;


import com.damoim.restapi.boards.entity.BoardType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "no")
public class TReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private Long boardId;

    private String writer;

    @Lob
    private String content;

    private Integer level;

    private Integer parentReplyNo;

    @Enumerated
    private BoardType boardType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parentReplyNo")
    private List<TReply> childReply = new ArrayList<>();

    @Builder.Default
    private Boolean closed = false;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;


    public boolean isClosed() {
        return this.closed == true;
    }

}
