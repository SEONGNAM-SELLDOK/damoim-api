package com.damoim.restapi.testReply;

import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsedItemReply {

    private UsedItem usedItem;
    private List<TReply> replyList;

}
