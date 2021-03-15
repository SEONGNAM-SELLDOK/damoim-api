package com.damoim.restapi.secondhandtrade.service;


import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.reply.entity.Reply;
import com.damoim.restapi.reply.model.response.ResponseUsedItemIncludeReply;
import com.damoim.restapi.reply.service.ReplyService;
import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.dao.UsedItemSearchRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import com.damoim.restapi.secondhandtrade.model.useditem.ResponseModifyUsedItemClosed;
import com.damoim.restapi.secondhandtrade.model.useditem.ResponseUsedItem;
import com.damoim.restapi.secondhandtrade.model.useditem.SearchUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.useditem.UsedItemRequest;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    public ResponseUsedItem save(UsedItemRequest request, RequestFile file) {
        UsedItem item = request.toEntity();
        if (file.nonNull()) {
            String upload = damoimFileUtil.upload(file);
            item.setTitleImg(upload);
        }
        usedItemRepository.save(item);
        return modelMapper.map(item, ResponseUsedItem.class);
    }

    public UsedItem save(UsedItemRequest request) {
        return usedItemRepository.save(request.toEntity());
    }

    public ResponseUsedItem selectItem(Long no) {
        return modelMapper.map(getItemFromId(no), ResponseUsedItem.class);
    }

    public ResponseUsedItem editItem(Long no, UsedItemRequest editRq) {
        UsedItem originItem = getItemFromId(no);
        UsedItem updateItem = usedItemRepository.save(editRq.updateTo(originItem));
        return modelMapper.map(updateItem, ResponseUsedItem.class);
    }

    public ResponseModifyUsedItemClosed itemUpdateToClosed(Long no, String writer) {
        UsedItem item = getItemFromId(no);
        if (!item.isWriter(writer)) {
            throw new AccessDeniedException("작성자 외 수정 불가능");
        }

        ResponseModifyUsedItemClosed closed = item.closed(writer);
        usedItemRepository.save(item);
        return closed;
    }

    public void delete(Long no) {
        UsedItem item = getItemFromId(no);
        usedItemRepository.delete(item);
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
        return ResponseUsedItemIncludeReply.toMapper(item, replyList);
    }

    public Page<ResponseUsedItem> search(SearchUsedItemRequest request, Pageable pageable) {
        return usedItemSearchRepository.search(request, pageable);
    }
}
