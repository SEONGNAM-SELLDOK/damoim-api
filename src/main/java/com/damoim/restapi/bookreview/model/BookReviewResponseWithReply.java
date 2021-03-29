package com.damoim.restapi.bookreview.model;

import com.damoim.restapi.reply.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/03/25
 */
@AllArgsConstructor
@Getter
public class BookReviewResponseWithReply {
    private BookReviewResponse bookReviewResponse;
    private List<Reply> replyList;
}
