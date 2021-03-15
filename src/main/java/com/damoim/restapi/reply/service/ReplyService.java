package com.damoim.restapi.reply.service;


import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.reply.dao.ChildReplyRepository;
import com.damoim.restapi.reply.dao.ReplyRepository;
import com.damoim.restapi.reply.entity.ChildReply;
import com.damoim.restapi.reply.entity.Reply;
import com.damoim.restapi.reply.model.request.RequestDeleteReply;
import com.damoim.restapi.reply.model.request.RequestSaveReply;
import com.damoim.restapi.reply.model.response.ResponseReply;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public ResponseReply replySave(Long boardId, RequestSaveReply requestSaveReply) {
        Long validBoardId = boardValidationService
            .existBoard(boardId, requestSaveReply.getBoardType());

        if (requestSaveReply.isParentReply()) {
            Reply reply = replyRepository.save(requestSaveReply.toEntity(validBoardId));
            return ResponseReply.of(validBoardId, reply);
        }
        if (requestSaveReply.isParentChildReply()) {
            Long parentId = requestSaveReply.getParentReplyId();
            Reply parentReply = getReply(parentId);
            if (parentReply.isClosed()) {
                throw new ReplyClosedException(parentId);
            }
            ChildReply childReply = modelMapper
                .map(requestSaveReply.toEntity(validBoardId), ChildReply.class);
            childReply.setParentReply(parentReply);
            childReply = childReplyRepository.save(childReply);

            return ResponseReply.of(validBoardId, childReply);
        }

        if (requestSaveReply.isChildChildReply()) {
            Long id = requestSaveReply.getParentReplyId();
            ChildReply parentChildReply = getChildReply(id);
            Reply parentReply = parentChildReply.getParentReply();
            ChildReply childReply = modelMapper
                .map(requestSaveReply.toEntity(validBoardId), ChildReply.class);
            childReply.setParentReply(parentReply);
            childReply
                .setContent("@" + parentChildReply.getWriter() + " " + childReply.getContent());
            childReply = childReplyRepository.save(childReply);
            return ResponseReply.of(validBoardId, childReply);
        }
        throw new IllegalArgumentException();
    }

    public void deleteReply(RequestDeleteReply requestDeleteReply) {
        if (requestDeleteReply.isParentId()) {
            Reply reply = getReplyIncludeChildList(requestDeleteReply.getReplyId());
            if (!reply.childListIsEmpty()) {
                reply.setContent("삭제된 댓글입니다.");
                reply.setClosed(true);
            } else {
                replyRepository.delete(reply);
            }
        }

        if (requestDeleteReply.isChildReplyId()) {
            ChildReply childReply = getChildReplyIncludeParentReply(
                requestDeleteReply.getReplyId());
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
        return replyRepository.findById(id).orElseThrow(getFoundPageSupplier(id));
    }

    private Reply getReplyIncludeChildList(Long id) {
        return replyRepository.findByIdIncludeChildList(id).orElseThrow(getFoundPageSupplier(id));
    }

    private Supplier<NotFoundResource> getFoundPageSupplier(Long id) {
        return () -> new NotFoundResource(HttpStatus.NOT_FOUND.toString(), String.valueOf(id));
    }

}
