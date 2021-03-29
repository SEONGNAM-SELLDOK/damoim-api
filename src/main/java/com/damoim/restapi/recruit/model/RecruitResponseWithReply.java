package com.damoim.restapi.recruit.model;

import com.damoim.restapi.reply.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/03/22
 */
@Getter
@AllArgsConstructor
public class RecruitResponseWithReply {
    private RecruitResponse recruitResponse;
    private List<Reply> replyList;
}
