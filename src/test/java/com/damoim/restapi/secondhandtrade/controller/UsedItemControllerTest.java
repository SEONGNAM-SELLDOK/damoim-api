package com.damoim.restapi.secondhandtrade.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.model.request.UsedItemRequest;
import com.damoim.restapi.secondhandtrade.service.UsedItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UsedItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UsedItemRepository usedItemRepository;

    @Autowired
    UsedItemService usedItemService;

    @Autowired
    ModelMapper modelMapper;


    @BeforeEach()
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    @DisplayName("중고거래 게시글 작성")
    @WithAccount("kjj@naver.com")
    void save() throws Exception {

        //given
        UsedItemRequest request = getItemRequest();
        String json = objectMapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mockMvc.perform(post("/useditems")
            .cookie(new Cookie("AUTH_TOKEN", "test_token"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());

        UsedItem returnItem = valueToObject(resultActions, UsedItem.class);

        //then
        UsedItem item = usedItemRepository.findById(returnItem.getNo()).get();
        assertThat(item.getTitle()).isEqualTo(returnItem.getTitle());
        assertThat(item.getCreateDate()).isEqualTo(returnItem.getCreateDate());
        assertThat(item.getWriter()).isEqualTo("kjj@naver.com");
    }

    @Test
    @DisplayName("카테고리 종류 보기")
    void category() throws Exception {
        mockMvc.perform(get("/useditems/categories")
            .cookie(new Cookie("AUTH_TOKEN", "testToken")))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("특정 게시글 가져오기")
    @WithAccount("kjj924@naver.com")
    void getPage() throws Exception {
        UsedItem item = usedItemService.save(getItemRequest());
        String id = String.valueOf(item.getNo());
        mockMvc.perform(get("/useditems/item/" + id))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("특정 게시글 가져오기-실패(없는 페이지번호)")
    @WithAccount("kjj@naver.com")
    void getPageFail() throws Exception {
        mockMvc.perform(get("/useditems/item/999"))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("statusCode").value("404"))
            .andExpect(jsonPath("message").value("404 NOT_FOUND"))
            .andExpect(jsonPath("inputValue").value("999"));
    }

    @Test
    @Transactional
    @DisplayName("게시글 판매상태 변경 - closed")
    @WithAccount("kjj@naver.com")
    void itemClosed() throws Exception {

        UsedItem item = usedItemService.save(getItemRequest());
        String id = String.valueOf(item.getNo());

        mockMvc.perform(patch("/useditems/item/" + id + "/closed"))
            .andDo(print())
            .andExpect(jsonPath("no").value(id))
            .andExpect(jsonPath("close").value(true))
            .andExpect(status().isOk());

        UsedItem usedItem = usedItemRepository.findById(item.getNo()).get();

        assertThat(usedItem.getEditWriter()).isEqualTo("kjj@naver.com");
        assertThat(usedItem.isClose()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("게시글 수정")
    @WithAccount("kjj@naver.com")
    void editItem() {
        //given
        UsedItem save = usedItemService.save(getItemRequest());
        //when
        UsedItemRequest itemRequest = getItemRequest();

        //then
        UsedItem usedItem = usedItemRepository.findById(save.getNo()).get();

        assertThat(usedItem.getNo()).isEqualTo(save.getNo());
        assertThat(usedItem.getEditWriter()).isEqualTo("kjj@naver.com");
    }

    @Test
    @Transactional
    @DisplayName("게시글 삭제")
    @WithAccount("kjj@naver.com")
    void itemDelete() {
        AuthUser principal = (AuthUser) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        //given
        UsedItem save = usedItemService.save(getItemRequest());
        //when
        usedItemService.delete(save.getNo(), principal);
        //then
        boolean result = usedItemRepository.existsById(save.getNo());
        assertThat(result).isFalse();
    }

    private <T> T valueToObject(ResultActions resultActions, Class<T> target) throws Exception {
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String resultJson = response.getContentAsString();
        return objectMapper.readValue(resultJson, target);
    }

    private UsedItemRequest getItemRequest() {
        return UsedItemRequest.builder()
            .address("tempAddress")
            .category(Category.DEFAULT)
            .description("content")
            .tradeType(TradeType.ALL)
            .title("mackbook")
            .price(10000)
            .build();
    }
}
