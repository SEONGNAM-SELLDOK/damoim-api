package com.damoim.restapi.reply.model.response;

import com.damoim.restapi.reply.entity.Reply;
import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
@Getter
public class ResponseUsedItemIncludeReply {

    private final long no;
    private final String title; //제목
    private final int price; //가격
    private final String writer; //등록자 ID
    private final String description; //내용
    private final TradeType tradeType; //거래 방식
    private final Category category; //물품별 카테고리 (default = 기타)
    private final String titleImg; //대표 이미지
    private final String address; //판매 지역
    private final LocalDateTime createDate; //등록일시
    private final LocalDateTime updateDate; //수정일시
    private final String editWriter; //수정자 ID
    private final boolean close;
    private final boolean negotiation;
    private final List<Reply> replyList;

    private ResponseUsedItemIncludeReply(UsedItem usedItem, List<Reply> replyList) {
        this.no = usedItem.getNo();
        this.title = usedItem.getTitle();
        this.price = usedItem.getPrice();
        this.writer = usedItem.getWriter();
        this.description = usedItem.getDescription();
        this.tradeType = usedItem.getTradeType();
        this.category = usedItem.getCategory();
        this.titleImg = usedItem.getTitle();
        this.address = usedItem.getAddress();
        this.createDate = usedItem.getCreateDate();
        this.updateDate = usedItem.getUpdateDate();
        this.editWriter = usedItem.getWriter();
        this.close = usedItem.isClose();
        this.negotiation = usedItem.isNegotiation();
        this.replyList = replyList;
    }

    public static ResponseUsedItemIncludeReply toMapper(UsedItem usedItem, List<Reply> replyList) {
        return new ResponseUsedItemIncludeReply(usedItem, replyList);
    }
}
