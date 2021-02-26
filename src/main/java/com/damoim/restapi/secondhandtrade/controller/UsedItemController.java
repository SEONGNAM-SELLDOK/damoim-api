package com.damoim.restapi.secondhandtrade.controller;

import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.ApiMessage;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import com.damoim.restapi.secondhandtrade.mapper.EnumMapper;
import com.damoim.restapi.secondhandtrade.mapper.EnumValue;
import com.damoim.restapi.secondhandtrade.model.EditUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.ResponseModifyUsedItemClosed;
import com.damoim.restapi.secondhandtrade.model.SaveUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.SearchUsedItemRequest;
import com.damoim.restapi.secondhandtrade.service.UsedItemService;

import io.swagger.annotations.Api;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

/**
 * Controller
 *
 * @author kimjaeJoon
 * @since 2021.02.22
 */
@Api(value = "UsedItem", tags = "중고거래 관련 REST API")
@RestController
@RequestMapping("useditems")
@RequiredArgsConstructor
public class UsedItemController {

  private final UsedItemService usedItemService;
  private final EnumMapper enumMapper;

  @ExceptionHandler(NotFoundPage.class)
  public ResponseEntity<ApiMessage> notFoundException(NotFoundPage notFoundPage) {
    ApiMessage apiMessage = ApiMessage.builder()
        .message(notFoundPage.getMessage())
        .inputValue(notFoundPage.getValue())
        .statusCode(HttpStatus.NOT_FOUND.value())
        .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiMessage);
  }


  @PostMapping
  public ResponseEntity<UsedItem> save(
      @Valid @RequestBody SaveUsedItemRequest saveUsedItemRequest) {
    UsedItem usedItem = usedItemService.save(saveUsedItemRequest);
    return new ResponseEntity<>(usedItem, HttpStatus.CREATED);
  }

  @GetMapping("/categories")
  public ResponseEntity<Map<String, List<EnumValue>>> categoryList() {
    //(수정 필요함) ENUM 타입으로 관리된 KEY 사용 예정
    Map<String, List<EnumValue>> category = enumMapper.get("category");
    return ResponseEntity.ok(category);
  }


  @GetMapping("/item/{no}")
  public ResponseEntity<UsedItem> selectItem(@PathVariable Long no) {
    UsedItem usedItem = usedItemService.selectItem(no);
    return ResponseEntity.ok(usedItem);
  }

  @GetMapping("/pages/search")
  public ResponseEntity<Page<UsedItem>> searchPage(SearchUsedItemRequest request,
      @PageableDefault(size = 6, sort = "postTime", direction = Direction.DESC) Pageable pageable) {
    Page<UsedItem> search = usedItemService.search(request, pageable);
    return ResponseEntity.ok(search);
  }

  @PutMapping("/item/{no}")
  public ResponseEntity<UsedItem> editItem(@PathVariable Long no,
      @Valid @RequestBody EditUsedItemRequest editRq){
    UsedItem item = usedItemService.editItem(no,editRq);
    return ResponseEntity.ok(item);
  }

  @PatchMapping("/item/{no}/closed")
  public ResponseEntity<ResponseModifyUsedItemClosed> closed(@PathVariable Long no,
      @RequestParam String writer) {
    return ResponseEntity.ok(usedItemService.itemUpdateToClosed(no, writer));
  }

  @DeleteMapping("/item/{no}")
  public ResponseEntity<Object> delete(@PathVariable Long no){
    usedItemService.delete(no);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
