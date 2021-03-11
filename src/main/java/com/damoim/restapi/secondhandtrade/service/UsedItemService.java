package com.damoim.restapi.secondhandtrade.service;


import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.secondhandtrade.dao.ReplyRepository;
import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.dao.UsedItemSearchRepository;
import com.damoim.restapi.secondhandtrade.entity.reply.Reply;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import com.damoim.restapi.secondhandtrade.model.reply.RequestReply;
import com.damoim.restapi.secondhandtrade.model.reply.ResponseReply;
import com.damoim.restapi.secondhandtrade.model.usedItem.ResponseModifyUsedItemClosed;
import com.damoim.restapi.secondhandtrade.model.usedItem.ResponseUsedItem;
import com.damoim.restapi.secondhandtrade.model.usedItem.ResponseUsedItemIncludeReply;
import com.damoim.restapi.secondhandtrade.model.usedItem.SearchUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.usedItem.UsedItemRequest;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsedItemService {

    private final UsedItemRepository usedItemRepository;
    private final UsedItemSearchRepository usedItemSearchRepository;
    private final ReplyRepository replyRepository;
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

    public ResponseUsedItemIncludeReply selectItem(Long no) {
        UsedItem item = usedItemRepository.findByIdJoinFetch(no)
            .orElseThrow(
                () -> new NotFoundPage(HttpStatus.NOT_FOUND.toString(), String.valueOf(no))
            );
        return modelMapper.map(item, ResponseUsedItemIncludeReply.class);
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
        return item
            .orElseThrow(
                () -> new NotFoundPage(HttpStatus.NOT_FOUND.toString(), String.valueOf(no)));
    }

    public Page<ResponseUsedItem> search(SearchUsedItemRequest request, Pageable pageable) {
        return usedItemSearchRepository.search(request, pageable);
    }

    public ResponseReply reply(Long no, RequestReply requestReply) {
        UsedItem item = getItemFromId(no);
        Long parentId = requestReply.getNo();
        Reply reply = null;
        if (Objects.isNull(parentId)) {
            reply = replyRepository.save(requestReply.toEntity(item));
        }

        if (Objects.nonNull(parentId)) {
            Reply parentReply = getReply(parentId);
            if (parentReply.isClosed()) {
                throw new RuntimeException("parentReply is closed");
            }
            reply = requestReply.toEntity(item);
            reply.setLevel(parentReply.getLevel() + 1);
            parentReply.getChildReply().add(reply);
            reply = replyRepository.save(reply);
        }

        return ResponseReply.of(item.getNo(), reply);
    }

    private Reply getReply(Long parentId) {
        return replyRepository.findById(parentId)
            .orElseThrow(
                () -> new NotFoundPage(HttpStatus.NOT_FOUND.toString(), String.valueOf(parentId))
            );
    }
}
