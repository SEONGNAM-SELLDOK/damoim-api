package com.damoim.restapi.secondhandtrade.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.model.SaveUsedItemRequest;
import com.damoim.restapi.secondhandtrade.service.UsedItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UsedItemControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UsedItemRepository usedItemRepository;

  @Autowired
  UsedItemService usedItemService;

  @Test
  @DisplayName("중고거래 게시글 작성")
  void save() throws Exception {

    //given
    SaveUsedItemRequest request = getItemRequest();
    String json = objectMapper.writeValueAsString(request);

    //when
    mockMvc.perform(post("/trading")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is2xxSuccessful());

    //then
    UsedItem item = usedItemRepository.findById(1L).get();
    assertThat(item.getTitle()).isEqualTo("mackbook");
    assertThat(item.getPostTime()).isBefore(LocalDateTime.now());
  }

  @Test
  @DisplayName("카테고리 종류 보기")
  void category() throws Exception {

    mockMvc.perform(get("/trading/category"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("페이징")
  void page() throws Exception {

    //given
    for (int i = 0; i < 10; i++) {
      usedItemService.save(getItemRequest());
    }

    //when
    mockMvc.perform(get("/trading/page")
        .param("page", "0"))
        .andDo(print())
        .andExpect(status().isOk());

  }

  @Test
  @DisplayName("페이징")
  void searchPage() throws Exception {

    //given
    for (int i = 0; i < 5; i++) {
      usedItemService.save(getItemRequest());
    }
    for (int i = 0; i < 10; i++) {
      SaveUsedItemRequest itemRequest = getItemRequest();
      itemRequest.setTitle("Air");
      usedItemService.save(itemRequest);
    }

    for (int i = 0; i < 3; i++) {
      SaveUsedItemRequest itemRequest = getItemRequest();
      itemRequest.setDescription("buy");
      usedItemService.save(itemRequest);
    }

    //when
    mockMvc.perform(get("/trading/page/search")
        .param("page", "2")
        .param("title", "Air")
        .param("description", "buy"))
        .andDo(print())
        .andExpect(status().isOk());

  }

  private SaveUsedItemRequest getItemRequest() {
    return SaveUsedItemRequest.builder()
        .writer("KJJ")
        .titleImg("temp")
        .address("tempAddress")
        .category(Category.DEFAULT)
        .description("content")
        .tradeType(TradeType.ALL)
        .title("mackbook")
        .price(10000)
        .build();
  }
}