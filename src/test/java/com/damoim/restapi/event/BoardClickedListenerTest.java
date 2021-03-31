package com.damoim.restapi.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.event.boardcount.dao.BoardCountRepository;
import com.damoim.restapi.event.boardcount.entity.BoardCount;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.model.request.UsedItemRequest;
import com.damoim.restapi.secondhandtrade.model.response.ResponseUsedItem;
import com.damoim.restapi.secondhandtrade.service.UsedItemService;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class BoardClickedListenerTest {

    @Autowired
    UsedItemService usedItemService;

    @Autowired
    BoardCountRepository boardCountRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @DisplayName("쓰레드 세이프 테스트 ")
    @WithAccount("kjj@email.com")
    void thread() throws Exception {
        ResponseUsedItem item =
            usedItemService.save(getItemRequest(), RequestFile.of("test", null));
        for (int i = 0; i < 1000; i++) {
            mockMvc.perform(get("/useditems/item/" + item.getNo() + "/reply")
                .cookie(new Cookie("AUTH_TOKEN", "testUser")))
                .andExpect(status().isOk());
        }
        BoardCount boardCount = boardCountRepository
            .findByBoardIdAndBoardType(item.getNo(), BoardType.USEDITEMS);
        long count = boardCountRepository.count();
        assertThat(count).isEqualTo(1);
        assertThat(boardCount.getClickCount()).isEqualTo(1000L);
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