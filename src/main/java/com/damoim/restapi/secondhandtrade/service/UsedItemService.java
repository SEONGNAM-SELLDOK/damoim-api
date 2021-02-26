package com.damoim.restapi.secondhandtrade.service;

import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.dao.UsedItemSearchRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import com.damoim.restapi.secondhandtrade.model.EditUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.ResponseModifyUsedItemClosed;
import com.damoim.restapi.secondhandtrade.model.SaveUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.SearchUsedItemRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsedItemService {

  private final UsedItemRepository usedItemRepository;
  private final ModelMapper modelMapper;
  private final UsedItemSearchRepository usedItemSearchRepository;

  public UsedItem save(SaveUsedItemRequest saveUsedItemRequest) {
    UsedItem item = modelMapper.map(saveUsedItemRequest, UsedItem.class);
    return usedItemRepository.save(item);
  }

  public UsedItem selectItem(Long no) {
    return getItemFromId(no);
  }

  public Page<UsedItem> defaultPage(Pageable pageable) {
    return usedItemRepository.findAll(pageable);
  }

  public UsedItem editItem(Long no, EditUsedItemRequest editRq) {
    UsedItem originItem = getItemFromId(no);
    UsedItem editItem = originItem.update(editRq);
    return usedItemRepository.save(editItem);
  }

  public ResponseModifyUsedItemClosed itemUpdateToClosed(Long no, String writer) {
    UsedItem item = getItemFromId(no);
    // 추후 SpringSecurity 의존성 추가 시  AccessDeniedException 으로 변경
    if (!item.isWriter(writer)) {
      throw new RuntimeException("작성자 외 수정 불가능");
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
        .orElseThrow(() -> new NotFoundPage(HttpStatus.NOT_FOUND.toString(), String.valueOf(no)));
  }

  public Page<UsedItem> search(SearchUsedItemRequest request, Pageable pageable) {
    return usedItemSearchRepository.search(request,pageable);
  }
}