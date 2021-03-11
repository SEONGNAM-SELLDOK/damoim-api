package com.damoim.restapi.secondhandtrade.controller;

import static com.damoim.restapi.secondhandtrade.controller.UsedItemController.ROOT;

import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.secondhandtrade.errormsg.ApiMessage;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import com.damoim.restapi.secondhandtrade.mapper.EnumMapper;
import com.damoim.restapi.secondhandtrade.mapper.EnumValue;
import com.damoim.restapi.secondhandtrade.model.ResponseModifyUsedItemClosed;
import com.damoim.restapi.secondhandtrade.model.ResponseUsedItem;
import com.damoim.restapi.secondhandtrade.model.SearchUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.UsedItemRequest;
import com.damoim.restapi.secondhandtrade.service.UsedItemService;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

  public static final String ROOT = "useditems";

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
  public ResponseEntity<ResponseUsedItem> save(
      @Valid @RequestBody UsedItemRequest usedItemRequest,
      @RequestParam(required = false) MultipartFile file) {
    ResponseUsedItem item = usedItemService.save(usedItemRequest, RequestFile.of(ROOT, file));
    return new ResponseEntity<>(item, HttpStatus.CREATED);
  }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, List<EnumValue>>> categoryList() {
        //(수정 필요함) ENUM 타입으로 관리된 KEY 사용 예정
        Map<String, List<EnumValue>> category = enumMapper.get("category");
        return ResponseEntity.ok(category);
    }


    @GetMapping("/item/{no}")
    public ResponseEntity<ResponseUsedItemIncludeReply> selectItem(@PathVariable Long no) {
        ResponseUsedItemIncludeReply item = usedItemService.selectItem(no);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/pages/search")
    public ResponseEntity<Page<ResponseUsedItem>> searchPage(SearchUsedItemRequest request,
        @PageableDefault(size = 6, sort = "postTime", direction = Direction.DESC) Pageable pageable) {
        Page<ResponseUsedItem> search = usedItemService.search(request, pageable);
        return ResponseEntity.ok(search);
    }

    @PutMapping("/item/{no}")
    public ResponseEntity<ResponseUsedItem> editItem(@PathVariable Long no,
        @Valid @RequestBody UsedItemRequest editRq) {
        ResponseUsedItem item = usedItemService.editItem(no, editRq);
        return ResponseEntity.ok(item);
    }

    @PatchMapping("/item/{no}/closed")
    public ResponseEntity<ResponseModifyUsedItemClosed> closed(@PathVariable Long no,
        @RequestParam String writer) {
        return ResponseEntity.ok(usedItemService.itemUpdateToClosed(no, writer));
    }

    @DeleteMapping("/item/{no}")
    public ResponseEntity<Object> delete(@PathVariable Long no) {
        usedItemService.delete(no);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/item/{no}/reply")
    public ResponseEntity<ResponseReply> addReply(@PathVariable Long no,
        @RequestBody RequestReply requestReply) {
        ResponseReply reply = usedItemService.reply(no, requestReply);
        return new ResponseEntity<>(reply, HttpStatus.CREATED);
    }
}
