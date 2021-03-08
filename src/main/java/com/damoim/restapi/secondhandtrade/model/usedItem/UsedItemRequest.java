package com.damoim.restapi.secondhandtrade.model.usedItem;

import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsedItemRequest {

  @NotNull
  @ApiModelProperty(value = "글 작성자(ID)", required = true, example = "dkansk924@naver.com")
  private String writer;

  @NotNull
  @ApiModelProperty(value = "글 제목", required = true, example = "맥북 팝니다.")
  private String title;

  @NotNull
  @Min(0)
  @ApiModelProperty(value = "물건 가격", required = true, example = "10000")
  private int price;

  @NotNull
  @ApiModelProperty(value = "글 내용", required = true, example = "2021년 신형입니다.")
  private String description;

  @NotNull
  @ApiModelProperty(value = "거래방식", required = true, example = "ALL")
  private TradeType tradeType;

  @ApiModelProperty(value = "썸네일(대표이미지)", example = "img/usedItem/IPad.png")
  private String titleImg;

  @ApiModelProperty(value = "카테고리 설정", example = "ELECTRONICS")
  private Category category;
  @ApiModelProperty(value = "흥정여부 설정", example = "true")
  private boolean negotiation;

  @ApiModelProperty(value = "판매위치 주소", example = "서울시중구")
  private String address;

  public UsedItem toEntity() {
    return UsedItem.builder()
        .writer(writer)
        .title(title)
        .price(price)
        .description(description)
        .tradeType(tradeType)
        .category(category)
        .negotiation(negotiation)
        .address(address)
        .build();
  }

  public UsedItem updateTo(UsedItem item) {
    item.setEditWriter(writer);
    item.setTitle(title);
    item.setPrice(price);
    item.setDescription(description);
    item.setTradeType(tradeType);
    item.setTitleImg(titleImg);
    item.setCategory(category);
    item.setNegotiation(negotiation);
    item.setAddress(address);
    return item;
  }

}
