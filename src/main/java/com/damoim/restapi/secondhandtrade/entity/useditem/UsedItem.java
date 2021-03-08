package com.damoim.restapi.secondhandtrade.entity.useditem;

import com.damoim.restapi.secondhandtrade.entity.reply.Reply;
import com.damoim.restapi.secondhandtrade.model.usedItem.ResponseModifyUsedItemClosed;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
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

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "no")
@AllArgsConstructor
@NoArgsConstructor
public class UsedItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long no;

  @Column(nullable = false)
  private String title; //제목

  @Column(nullable = false)
  private int price; //가격

  @Column(nullable = false)
  private String writer; //등록자 ID

  @Lob
  @Column(nullable = false)
  private String description; //내용

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TradeType tradeType; //거래 방식

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Category category = Category.DEFAULT; //물품별 카테고리 (default = 기타)

  private String titleImg; //대표 이미지

  private String address; //판매 지역

  @CreationTimestamp
  private LocalDateTime createDate; //등록일시

  @UpdateTimestamp
  private LocalDateTime updateDate; //수정일시

  private String editWriter; //수정자 ID

  @OneToMany(mappedBy = "usedItem")
  @JsonManagedReference
  List<Reply> replyList = new ArrayList<>();

  @Builder.Default
  private boolean close = false; //판매완료 여부 (default = false)
  @Builder.Default
  private boolean negotiation = false; //흥정여부(default = false)

  public boolean isWriter(String writer) {
    return this.writer.equals(writer);
  }

  public ResponseModifyUsedItemClosed closed(String writer) {
    this.editWriter = writer;
    this.close = true;
    return new ResponseModifyUsedItemClosed(this.no, true);
  }
}
