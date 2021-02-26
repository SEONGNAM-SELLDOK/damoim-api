package com.damoim.restapi.secondhandtrade.entity.useditem;

import com.damoim.restapi.secondhandtrade.model.EditUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.ResponseModifyUsedItemClosed;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
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
  private Category category = Category.DEFAULT; //물품별 카테고리 (default = 기타)

  private String titleImg; //대표 이미지

  private String address; //판매 지역

  @CreationTimestamp
  private LocalDateTime postTime; //등록일시

  @UpdateTimestamp
  private LocalDateTime editTime; //수정일시

  private String editWriter; //수정자 ID

  private boolean close = false; //판매완료 여부 (default = false)

  private boolean negotiation = false; //흥정여부(default = false)

  public boolean isWriter(String writer){
    return this.writer.equals(writer);
  }

  public ResponseModifyUsedItemClosed closed(String writer){
    this.editWriter = writer;
    this.close=true;
    return new ResponseModifyUsedItemClosed(this.no, true);
  }

  public UsedItem update(EditUsedItemRequest editRq) {
    this.editWriter = editRq.getEditWriter();
    this.title =editRq.getTitle();
    this.price = editRq.getPrice();
    this.description = editRq.getDescription();
    this.tradeType = editRq.getTradeType();
    this.titleImg = editRq.getTitleImg();
    this.category = editRq.getCategory();
    this.negotiation = editRq.isNegotiation();
    this.address = editRq.getAddress();
    return this;
  }

}
