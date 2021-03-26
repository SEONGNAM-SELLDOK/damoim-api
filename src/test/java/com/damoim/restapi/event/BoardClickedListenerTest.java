package com.damoim.restapi.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.event.boardcount.dao.BoardCountRepository;
import com.damoim.restapi.event.boardcount.entity.BoardCount;
import com.damoim.restapi.secondhandtrade.controller.WithAccount;
import com.damoim.restapi.secondhandtrade.entity.useditem.Category;
import com.damoim.restapi.secondhandtrade.entity.useditem.TradeType;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.model.request.UsedItemRequest;
import com.damoim.restapi.secondhandtrade.service.UsedItemService;
import java.time.LocalDate;
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
    @DisplayName("게시글 카운트")
    @WithAccount("kjj@email.com")
    void boardCount() throws Exception {
        UsedItem usedItem = usedItemService.save(getItemRequest());
        mockMvc.perform(get("/useditems/item/" + usedItem.getNo() + "/reply")
            .cookie(new Cookie("AUTH_TOKEN", "testUser")))
            .andExpect(status().isOk());

        BoardCount boardCount = boardCountRepository
            .findByBoardIdAndBoardType(usedItem.getNo(), BoardType.USEDITEMS);

        assertThat(boardCount.getClickCount()).isEqualTo(1L);
        assertThat(boardCount.getWeekClickCount()).isEqualTo(1L);
        assertThat(boardCount.getRecordDate()).isEqualTo(LocalDate.now());
        assertThat(boardCount.getBoardType()).isEqualTo(BoardType.USEDITEMS);
    }

    @Test
    @DisplayName("쓰레드 세이프 테스트")
    @WithAccount("kjj@email.com")
    void thread() throws Exception {
        UsedItem usedItem = usedItemService.save(getItemRequest());
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(get("/useditems/item/" + usedItem.getNo() + "/reply")
                .cookie(new Cookie("AUTH_TOKEN", "testUser")))
                .andExpect(status().isOk());
        }
        BoardCount boardCount = boardCountRepository
            .findByBoardIdAndBoardType(usedItem.getNo(), BoardType.USEDITEMS);
        assertThat(boardCount.getClickCount()).isEqualTo(10L);
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