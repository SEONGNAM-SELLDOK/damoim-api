package com.damoim.restapi.secondhandtrade.service;


import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.event.BoardClickedEvent;
import com.damoim.restapi.event.BoardCreatedEvent;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.reply.entity.Reply;
import com.damoim.restapi.reply.model.response.ResponseUsedItemIncludeReply;
import com.damoim.restapi.reply.service.ReplyService;
import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.dao.UsedItemSearchRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import com.damoim.restapi.secondhandtrade.model.request.SearchUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.request.UsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.response.ResponseModifyUsedItemClosed;
import com.damoim.restapi.secondhandtrade.model.response.ResponseUsedItem;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dkansk924@naver.com
 * @since 2021. 02. 10
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UsedItemService {

    private final UsedItemRepository usedItemRepository;
    private final UsedItemSearchRepository usedItemSearchRepository;
    private final ReplyService replyService;
    private final DamoimFileUtil damoimFileUtil;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    public ResponseUsedItem save(UsedItemRequest request, RequestFile file) {
        UsedItem item = request.toEntity();
        if (file.nonNull()) {
            String upload = damoimFileUtil.upload(file);
            item.setTitleImg(upload);
        }
        item = usedItemRepository.save(item);
        eventPublisher.publishEvent(new BoardCreatedEvent(item.getNo(), BoardType.USEDITEMS));
        return modelMapper.map(item, ResponseUsedItem.class);
    }

    public UsedItem save(UsedItemRequest request) {
        return usedItemRepository.save(request.toEntity());
    }

    public ResponseUsedItem selectItem(Long no) {
        return modelMapper.map(getItemFromId(no), ResponseUsedItem.class);
    }

    public ResponseUsedItem editItem(Long no, UsedItemRequest editRq, AuthUser authUser) {
        UsedItem originItem = enableEditUser(no, authUser);
        UsedItem updateItem = usedItemRepository.save(editRq.updateTo(originItem));
        return modelMapper.map(updateItem, ResponseUsedItem.class);
    }

    public ResponseModifyUsedItemClosed itemUpdateToClosed(Long no, AuthUser authUser) {
        UsedItem item = enableEditUser(no, authUser);
        ResponseModifyUsedItemClosed closed = item.closed(authUser.getEmail());
        usedItemRepository.save(item);
        return closed;
    }

    public void delete(Long no, AuthUser authUser) {
        UsedItem item = enableEditUser(no, authUser);
        usedItemRepository.delete(item);
    }

    private UsedItem enableEditUser(Long id, AuthUser authUser) {
        UsedItem usedItem = getItemFromId(id);
        if (!usedItem.isWriter(authUser.getEmail())) {
            throw new AccessDeniedException("작성자 외 변경 불가능");
        }
        return usedItem;
    }

    private UsedItem getItemFromId(Long no) {
        Optional<UsedItem> item = usedItemRepository.findById(no);
        return item.orElseThrow(
            () -> new NotFoundResource(HttpStatus.NOT_FOUND.toString(), String.valueOf(no))
        );
    }

    public ResponseUsedItemIncludeReply getUsedItemIncludeReply(Long boardId, BoardType boardType) {
        UsedItem item = getItemFromId(boardId);
        List<Reply> replyList = replyService.getReplyList(boardType, boardId);
        eventPublisher.publishEvent(new BoardClickedEvent(boardId, boardType));
        return ResponseUsedItemIncludeReply.toMapper(item, replyList);
    }

    public Page<ResponseUsedItem> search(SearchUsedItemRequest request, Pageable pageable) {
        return usedItemSearchRepository.search(request, pageable);
    }
}
