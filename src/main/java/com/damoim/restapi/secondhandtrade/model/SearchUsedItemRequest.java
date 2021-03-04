package com.damoim.restapi.secondhandtrade.model;

import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;

}
