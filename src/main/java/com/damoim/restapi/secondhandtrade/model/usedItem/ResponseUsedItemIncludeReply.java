package com.damoim.restapi.secondhandtrade.model.usedItem;

import com.damoim.restapi.secondhandtrade.entity.reply.Reply;
import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUsedItemIncludeReply {

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
    private Set<Reply> replyList;
}
