package com.damoim.restapi.secondhandtrade.model;

import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SaveUsedItemRequest {
  @NotNull
  @ApiModelProperty(value = "글 작성자(ID)",required = true,example = "dkansk924@naver.com")
  private String writer;

  @NotNull
  @ApiModelProperty(value = "글 제목",required = true,example = "맥북 팝니다.")
  private String title;

  @NotNull
  @Min(0)
  @ApiModelProperty(value = "물건 가격",required = true,example = "10000")
  private int price;

  @NotNull
  @ApiModelProperty(value = "글 내용",required = true,example = "2021년 신형입니다.")
  private String description;

  @NotNull
  @ApiModelProperty(value ="거래방식",required = true,example = "ALL")
  private TradeType tradeType;

  @ApiModelProperty(value = "썸네일(대표이미지)",example ="img/usedItem/MackBook.png" )
  private String titleImg;

  @ApiModelProperty(value = "카테고리 설정",example = "ELECTRONICS")
  private Category category;
  @ApiModelProperty(value = "흥정여부 설정",example = "true")
  private boolean negotiation;

  @ApiModelProperty(value = "판매위치 주소", example = "서울시중구")
  private String address;

}
