package com.damoim.restapi.secondhandtrade.model.response;

import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUsedItem {

  private long no;
  private String title; //제목
  private int price; //가격
  private String writer; //등록자 ID
  private String description; //내용
  private TradeType tradeType; //거래 방식
  private Category category; //물품별 카테고리 (default = 기타)
  private String titleImg; //대표 이미지
  private String address; //판매 지역
  private LocalDateTime createDate; //등록일시
  private LocalDateTime updateDate; //수정일시
  private String editWriter; //수정자 ID
  private boolean close;
  private boolean negotiation;

  @QueryProjection
  public ResponseUsedItem(long no, String title, int price, String writer,
      String description, TradeType tradeType,
      Category category, String titleImg, String address, LocalDateTime createDate,
      LocalDateTime updateDate, String editWriter, boolean close, boolean negotiation) {
    this.no = no;
    this.title = title;
    this.price = price;
    this.writer = writer;
    this.description = description;
    this.tradeType = tradeType;
    this.category = category;
    this.titleImg = titleImg;
    this.address = address;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.editWriter = editWriter;
    this.close = close;
    this.negotiation = negotiation;
  }
}
