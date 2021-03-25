package com.damoim.restapi.lecture.dao;

import com.damoim.restapi.lecture.model.LectureResponse;
import com.damoim.restapi.reply.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/03/25
 */
@Getter
@AllArgsConstructor
public class LectureResponseWithReply {
    private LectureResponse lectureResponse;
    private List<Reply> replyList;
}
