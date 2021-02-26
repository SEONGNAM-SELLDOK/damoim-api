package com.damoim.restapi.secondhandtrade.model;

import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchUsedItemRequest {
  private String title;
  private Integer price;
  private String writer;
  private String description;
  private TradeType tradeType;
  private Category category;
  private String address;
  private String editWriter;
  private Boolean close;
  private Boolean negotiation;

}
