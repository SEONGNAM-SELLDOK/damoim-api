package com.damoim.restapi.secondhandtrade.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.model.EditUsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.SaveUsedItemRequest;
import com.damoim.restapi.secondhandtrade.service.UsedItemService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

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

  @Autowired
  ModelMapper modelMapper;



  @Test
  @Transactional
  @DisplayName("중고거래 게시글 작성")
  void save() throws Exception {

    //given
    SaveUsedItemRequest request = getItemRequest();
    String json = objectMapper.writeValueAsString(request);

    //when
    ResultActions resultActions = mockMvc.perform(post("/useditems")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is2xxSuccessful());

    UsedItem returnItem = valueToObject(resultActions,UsedItem.class);

    //then
    UsedItem item = usedItemRepository.findById(returnItem.getNo()).get();
    assertThat(item.getTitle()).isEqualTo(returnItem.getTitle());
    assertThat(item.getPostTime()).isEqualTo(returnItem.getPostTime());
  }



  @Test
  @DisplayName("카테고리 종류 보기")
  void category() throws Exception {

    mockMvc.perform(get("/useditems/categories"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @Transactional
  @DisplayName("특정 게시글 가져오기")
  void getPage() throws Exception{
    UsedItem item = usedItemService.save(getItemRequest());
    String id = String.valueOf(item.getNo());
    mockMvc.perform(get("/useditems/item/"+id))
        .andExpect(status().isOk());
  }

  @Test
  @Transactional
  @DisplayName("특정 게시글 가져오기-실패(없는 페이지번호)")
  void getPageFail() throws Exception{
    mockMvc.perform(get("/useditems/item/999"))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("statusCode").value("404"))
        .andExpect(jsonPath("message").value("404 NOT_FOUND"))
        .andExpect(jsonPath("inputValue").value("999"));
  }


  @Test
  @Transactional
  @DisplayName("게시글 판매상태 변경 - closed")
  void itemClosed() throws Exception{

    UsedItem item = usedItemService.save(getItemRequest());
    String id = String.valueOf(item.getNo());

    mockMvc.perform(patch("/useditems/item/"+id+"/closed")
        .param("writer","KJJ"))
        .andExpect(jsonPath("no").value(id))
        .andExpect(jsonPath("close").value(true))
        .andExpect(status().isOk());

    UsedItem usedItem = usedItemRepository.findById(item.getNo()).get();

    assertThat(usedItem.isClose()).isTrue();
  }

  @Test
  @Transactional
  @DisplayName("게시글 수정")
  void editItem(){
    //given
    UsedItem save = usedItemService.save(getItemRequest());
    //when
    EditUsedItemRequest request = modelMapper.map(getItemRequest(), EditUsedItemRequest.class);
    request.setEditWriter("수정자 이름");
    usedItemService.editItem(save.getNo(), request);

    //then
    UsedItem usedItem = usedItemRepository.findById(save.getNo()).get();

    assertThat(usedItem.getNo()).isEqualTo(save.getNo());
    assertThat(usedItem.getEditWriter()).isEqualTo(request.getEditWriter());

  }

  @Test
  @Transactional
  @DisplayName("게시글 삭제")
  void itemDelete(){
    //given
    UsedItem save = usedItemService.save(getItemRequest());
    //when
    usedItemService.delete(save.getNo());
    //then
    boolean result = usedItemRepository.existsById(save.getNo());
    assertThat(result).isFalse();
  }


  private <T> T valueToObject(ResultActions resultActions, Class<T> target) throws Exception{
    MvcResult mvcResult = resultActions.andReturn();
    MockHttpServletResponse response = mvcResult.getResponse();
    String resultJson = response.getContentAsString();
    return objectMapper.readValue(resultJson,target);
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