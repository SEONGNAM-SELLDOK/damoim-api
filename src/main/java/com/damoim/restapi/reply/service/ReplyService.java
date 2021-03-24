package com.damoim.restapi.reply.service;


import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.reply.dao.ChildReplyRepository;
import com.damoim.restapi.reply.dao.ReplyRepository;
import com.damoim.restapi.reply.entity.ChildReply;
import com.damoim.restapi.reply.entity.Reply;
import com.damoim.restapi.reply.model.request.RequestDeleteReply;
import com.damoim.restapi.reply.model.request.RequestEditReply;
import com.damoim.restapi.reply.model.request.RequestSaveReply;
import com.damoim.restapi.reply.model.response.ResponseEditReply;
import com.damoim.restapi.reply.model.response.ResponseReply;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ChildReplyRepository childReplyRepository;
    private final BoardValidationService boardValidationService;

    public ResponseReply replySave(Long boardId, RequestSaveReply requestSaveReply) {
        Long replyId = requestSaveReply.getParentReplyId();
        Long provenBoardId = boardValidationService
            .existBoard(boardId, requestSaveReply.getBoardType());

        if (requestSaveReply.isParentReply()) {
            Reply newReply = replyRepository.save(requestSaveReply.toReply(provenBoardId));
            return ResponseReply.of(provenBoardId, newReply);
        }

        if (requestSaveReply.isParentChildReply()) {
            Reply parentReply = getReply(replyId);
            ChildReply newReply = childReplyRepository
                .save(requestSaveReply.toChildReply(parentReply));
            return ResponseReply.of(provenBoardId, newReply);
        }

        if (requestSaveReply.isChildChildReply()) {
            ChildReply targetReply = getChildReply(replyId);
            ChildReply newReply = requestSaveReply.toChildReply(targetReply.getParentReply());
            String newContent = "@" + targetReply.getWriter() + " " + newReply.getContent();
            newReply.updateContent(newContent);
            newReply = childReplyRepository.save(newReply);
            return ResponseReply.of(provenBoardId, newReply);
        }
        throw new IllegalArgumentException();
    }

    public ResponseEditReply editReply(Long id, RequestEditReply requestEditReply) {
        String newContent = requestEditReply.getContent();
        if (requestEditReply.isParentReply()) {
            Reply reply = getReply(id);
            reply.updateContent(newContent);
            replyRepository.save(reply);
            return ResponseEditReply.of(reply.getNo(), reply.getContent());
        }
        ChildReply childReply = getChildReply(id);
        childReply.updateContent(newContent);
        childReplyRepository.save(childReply);
        return ResponseEditReply.of(childReply.getNo(), childReply.getContent());
    }

    public void deleteReply(RequestDeleteReply requestDeleteReply) {
        Long replyId = requestDeleteReply.getReplyId();
        if (requestDeleteReply.isParentId()) {
            Reply reply = getReplyIncludeChildList(replyId);
            if (!reply.childListIsEmpty()) {
                reply.closed();
                replyRepository.save(reply);
            } else {
                replyRepository.delete(reply);
            }
        }

        if (requestDeleteReply.isChildReplyId()) {
            ChildReply childReply = getChildReplyIncludeParentReply(replyId);
            Reply parentReply = childReply.getParentReply();
            parentReply.getChildReply().remove(childReply);
            if (parentReply.childListIsEmpty() && parentReply.isClosed()) {
                replyRepository.delete(parentReply);
            }
            childReplyRepository.delete(childReply);
        }
    }

    public List<Reply> getReplyList(BoardType boardType, Long boardId) {
        return replyRepository.parentList(boardType, boardId);
    }

    private ChildReply getChildReplyIncludeParentReply(Long id) {
        return childReplyRepository.getChildReplyIncludeParentReply(id)
            .orElseThrow(getFoundPageSupplier(id));
    }

    private ChildReply getChildReply(Long id) {
        return childReplyRepository.findById(id).orElseThrow(getFoundPageSupplier(id));
    }

    private Reply getReply(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(getFoundPageSupplier(id));
        reply.checkClosed();
        return reply;
    }

    private Reply getReplyIncludeChildList(Long id) {
        return replyRepository.findByIdIncludeChildList(id).orElseThrow(getFoundPageSupplier(id));
    }

    private Supplier<NotFoundResource> getFoundPageSupplier(Long id) {
        return () -> new NotFoundResource(HttpStatus.NOT_FOUND.toString(), String.valueOf(id));
    }

}
