package com.damoim.restapi.event;

import com.damoim.restapi.event.boardcount.dao.BoardCountRepository;
import com.damoim.restapi.event.boardcount.entity.BoardCount;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Async
@Component
@RequiredArgsConstructor
public class BoardClickedListener {

    private final BoardCountRepository boardCountRepository;


    @EventListener
    @Transactional
    public synchronized void handleBoardClickedEvent(BoardClickedEvent clickedEvent) {
        BoardCount boardCount = boardCountRepository
            .findByBoardIdAndBoardType(clickedEvent.getBoardId(), clickedEvent.getBoardType());

        if (Objects.isNull(boardCount)) {
            boardCountRepository.save(clickedEvent.toEntity());
        }

        if (Objects.nonNull(boardCount)) {
            if (boardCount.isOneWeekAgo()) {
                boardCount.updateRecordDateAndResetWeekClickCount();
            }
            boardCount.plusCount();
            boardCountRepository.save(boardCount);
        }
    }
}
