package com.damoim.restapi.secondhandtrade.model.usedItem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseModifyUsedItemClosed {

  private final Long no;
  private final boolean close;

}