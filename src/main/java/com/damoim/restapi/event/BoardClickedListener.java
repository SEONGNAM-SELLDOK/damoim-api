package com.damoim.restapi.event;

import com.damoim.restapi.event.boardcount.dao.BoardCountRepository;
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
    public void handleBoardCreateEvent(BoardCreatedEvent event) {
        boardCountRepository.save(event.toEntity());
    }

    @EventListener
    @Transactional
    public synchronized void handleBoardClickedEvent(BoardClickedEvent clickedEvent) {
        boardCountRepository.updateCount(clickedEvent.getBoardId(), clickedEvent.getBoardType());
    }
}
