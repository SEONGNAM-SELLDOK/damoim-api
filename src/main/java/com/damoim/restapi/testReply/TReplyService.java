package com.damoim.restapi.testReply;


import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TReplyService {

    private final TReplyRepository replyRepository;

    public TResponseReply replySave(Long id, TRequestReply requestReply) {
        Long parentId = requestReply.getNo();
        TReply reply = null;
        if (Objects.isNull(parentId)) {
            TReply entity = requestReply.toEntity();
            entity.setBoardId(id);
            reply = replyRepository.save(entity);
        }

        if (Objects.nonNull(parentId)) {
            TReply parentReply = getReply(parentId);
            if (parentReply.isClosed()) {
                throw new RuntimeException("parentReply is closed");
            }
            reply = requestReply.toEntity();
            reply.setBoardId(id);
            reply.setLevel(parentReply.getLevel() + 1);
            parentReply.getChildReply().add(reply);
            reply = replyRepository.save(reply);
        }
        return TResponseReply.of(id, reply);
    }

    public void deleteReply(Long replyId) {
        TReply reply = getReply(replyId);

        if (!reply.getChildReply().isEmpty()) {
            reply.setContent("삭제된 댓글입니다.");
            reply.setClosed(true);
        }

    }

    private TReply getReply(Long parentId) {
        return replyRepository.findById(parentId)
            .orElseThrow(
                () -> new NotFoundPage(HttpStatus.NOT_FOUND.toString(), String.valueOf(parentId))
            );
    }
}
