package com.damoim.restapi.secondhandtrade.entity.reply;

import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String writer;

    @Lob
    private String content;

    private Integer level;

    private Integer parentReplyNo;

    @ManyToOne
    @JsonBackReference
    private UsedItem usedItem;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parentReplyNo")
    private List<Reply> childReply = new ArrayList<>();

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
