package com.damoim.restapi.secondhandtrade.service;

import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import com.damoim.restapi.secondhandtrade.model.SaveUsedItemRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsedItemService {

  private final UsedItemRepository usedItemRepository;
  private final ModelMapper modelMapper;

  public UsedItem save(SaveUsedItemRequest saveUsedItemRequest) {
    UsedItem item = modelMapper.map(saveUsedItemRequest, UsedItem.class);
    item.setPostTime(LocalDateTime.now());
    return usedItemRepository.save(item);
  }

  public UsedItem selectItem(Long no) {
    Optional<UsedItem> item = usedItemRepository.findById(no);
    return item.orElseThrow(()-> new NotFoundPage(HttpStatus.NOT_FOUND.toString(),no));
  }

  public Page<UsedItem> defaultPage(Pageable pageable) {
    return usedItemRepository.findAll(pageable);
  }

  public Page<UsedItem> searchTitleOrDescription(String title, String description,
      Pageable pageable) {
    return usedItemRepository.findAllSearch(title, description, pageable);
  }


}
