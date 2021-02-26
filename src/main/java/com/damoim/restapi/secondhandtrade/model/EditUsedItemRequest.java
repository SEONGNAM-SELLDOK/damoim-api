package com.damoim.restapi.secondhandtrade.model;

import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUsedItemRequest {
  @NotNull
  @ApiModelProperty(value = "글 수정자",required = true,example = "dkansk924@naver.com")
  private String editWriter;

  @NotNull
  @ApiModelProperty(value = "글 제목",required = true,example = "(수정)아이패드 팝니다.")
  private String title;

  @NotNull
  @Min(0)
  @ApiModelProperty(value = "물건 가격",required = true,example = "12000")
  private int price;

  @NotNull
  @ApiModelProperty(value = "글 내용",required = true,example = "2020년 구형 입니다.")
  private String description;

  @NotNull
  @ApiModelProperty(value ="거래방식",required = true,example = "DELIVERY")
  private TradeType tradeType;

  @NotNull
  @ApiModelProperty(value = "썸네일(대표이미지)",example ="img/usedItem/IPad.png" )
  private String titleImg;

  @NotNull
  @ApiModelProperty(value = "카테고리 설정",example = "ELECTRONICS")
  private Category category;

  @NotNull
  @ApiModelProperty(value = "흥정여부 설정",example = "true")
  private boolean negotiation;

  @NotNull
  @ApiModelProperty(value = "판매위치 주소", example = "서울시중구")
  private String address;
}
