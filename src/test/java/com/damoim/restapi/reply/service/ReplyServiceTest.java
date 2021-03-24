package com.damoim.restapi.reply.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitResponse;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.service.RecruitService;
import com.damoim.restapi.reply.dao.ReplyRepository;
import com.damoim.restapi.reply.entity.Reply;
import com.damoim.restapi.reply.model.request.RequestDeleteReply;
import com.damoim.restapi.reply.model.request.RequestSaveReply;
import com.damoim.restapi.reply.model.response.ResponseReply;
import com.damoim.restapi.reply.model.response.ResponseUsedItemIncludeReply;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import com.damoim.restapi.secondhandtrade.model.request.UsedItemRequest;
import com.damoim.restapi.secondhandtrade.service.UsedItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class ReplyServiceTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UsedItemService usedItemService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ReplyService replyService;

    @Autowired
    RecruitService recruitService;

    @Autowired
    ReplyRepository replyRepository;


    @Test
    @DisplayName("댓글 등록")
    @WithAccount("kjj@naver.com")
    void saveReply() {
        UsedItem usedItem = usedItemService.save(getItemRequest());
        RecruitResponse recruit = recruitService.save(getRecruitSaveRequest(), null);

        long usedItemNo = usedItem.getNo();
        RequestSaveReply requestSaveReply = getSaveReply();
        RequestSaveReply reply = requestSaveReply.checkUrl("usedItems"); // board type setting

        replyService.replySave(usedItemNo, reply);// 부모 댓글 저장

        requestSaveReply.setParentReplyId(1L);
        replyService.replySave(usedItemNo, reply); // 부모의 자식 댓글 저장

        requestSaveReply.setIsChildId(true);
        replyService.replySave(usedItemNo, reply); // 자식의 자식 댓글 저장

        RequestSaveReply saveReply = getSaveReply();
        saveReply.setBoardType(BoardType.RECRUIT);
        replyService.replySave(recruit.getId(), saveReply); // 다른 보드타입 댓글 저장.

        ResponseUsedItemIncludeReply usedItemIncludeReply = usedItemService
            .getUsedItemIncludeReply(usedItemNo, BoardType.USEDITEMS);

        List<Reply> replyList = usedItemIncludeReply.getReplyList();
        assertThat(replyList.size()).isEqualTo(1);
        assertThat(replyList.get(0).getChildReply().size()).isEqualTo(2);
        assertThat(replyList.get(0).getChildReply().get(1).getContent())
            .isEqualTo("@kjj testContent");
    }

    @Test
    @DisplayName("저장실패 - 존재하지않는 게시판")
    void replySave_fail_notFound_Board() {

        RequestSaveReply reply = getSaveReply();
        reply.setBoardType(BoardType.USEDITEMS);

        assertThatThrownBy(() -> replyService.replySave(9999L, reply))
            .isInstanceOf(NotFoundResource.class);
    }

    @Test
    @DisplayName("저장실패 - 존재하지않는 부모 댓글")
    @WithAccount("kjj@naver.com")
    void replySave_fail_NotFound_ParentReply() {
        UsedItem usedItem = usedItemService.save(getItemRequest());
        long usedItemNo = usedItem.getNo();
        RequestSaveReply reply = getSaveReply();
        reply.setBoardType(BoardType.USEDITEMS);
        reply.setParentReplyId(999L);

        assertThatThrownBy(() -> replyService.replySave(usedItemNo, reply))
            .isInstanceOf(NotFoundResource.class);
    }

    @Test
    @DisplayName("저장실패 - 존재하지않는 부모의 자식의 댓글 ")
    @WithAccount("kjj@naver.com")
    void replySave_fail_NotFound_ReplyChildReply() {
        UsedItem usedItem = usedItemService.save(getItemRequest());
        long usedItemNo = usedItem.getNo();

        RequestSaveReply reply = getSaveReply();
        reply.setBoardType(BoardType.USEDITEMS);
        replyService.replySave(usedItemNo, reply);// 부모 댓글 저장

        reply.setParentReplyId(1L);
        replyService.replySave(usedItemNo, reply); // 부모의 자식 댓글 저장

        reply.setBoardType(BoardType.USEDITEMS);
        reply.setIsChildId(true);
        reply.setParentReplyId(999L); //존재하지 않는 댓글 번호 설정.

        assertThatThrownBy(() -> replyService.replySave(usedItemNo, reply))
            .isInstanceOf(NotFoundResource.class);
    }

    @Test
    @DisplayName("댓글삭제 - 자식이 없는 경우")
    @WithAccount("kjj@naver.com")
    void replyDelete_None_ChildReply() {
        UsedItem usedItem = usedItemService.save(getItemRequest());
        long usedItemNo = usedItem.getNo();
        RequestSaveReply requestSaveReply = getSaveReply();
        requestSaveReply.setBoardType(BoardType.USEDITEMS);

        ResponseReply parentReply = replyService
            .replySave(usedItemNo, requestSaveReply);// 부모 댓글 저장
        RequestDeleteReply rq = new RequestDeleteReply(parentReply.getReply().getNo(), false);
        replyService.deleteReply(rq);
        boolean result = replyRepository.existsById(rq.getReplyId());

        assertThat(result).isFalse();
    }


    private RecruitSaveRequest getRecruitSaveRequest() {
        return RecruitSaveRequest.builder()
            .company("Naver")
            .title("서비스를 함께할 팀원을 모집합니다.")
            .location("판교")
            .reward(500)
            .deadline(LocalDate.of(2022, 2, 1))
            .build();
    }

    private RequestSaveReply getSaveReply() {
        RequestSaveReply requestSaveReply = new RequestSaveReply();
        requestSaveReply.setWriter("kjj");
        requestSaveReply.setContent("testContent");
        requestSaveReply.setIsChildId(false);
        return requestSaveReply;
    }

    private UsedItemRequest getItemRequest() {
        return UsedItemRequest.builder()
            .address("tempAddress")
            .category(Category.DEFAULT)
            .description("content")
            .tradeType(TradeType.ALL)
            .title("mackbook")
            .price(10000)
            .build();
    }

}