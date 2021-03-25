package com.damoim.restapi.event;

import com.damoim.restapi.event.boardcount.dao.BoardCountRepository;
import com.damoim.restapi.event.boardcount.entity.BoardCount;
import java.util.Objects;
import javax.persistence.EntityManager;
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
    private final EntityManager entityManager;

    @EventListener
    @Transactional
    public void handleBoardClickedEvent(BoardClickedEvent clickedEvent) {

        entityManager.clear();
        BoardCount boardCount = boardCountRepository
            .findByBoardIdAndBoardType(clickedEvent.getBoardId(), clickedEvent.getBoardType());

        System.out.println("findBy = " + boardCount);

        if (Objects.isNull(boardCount)) {
            BoardCount boardCount1 = boardCountRepository.save(clickedEvent.toEntity());
            System.out.println("SAVE = " + boardCount1);
        }

        if (Objects.nonNull(boardCount)) {
            if (boardCount.isOneWeekAgo()) {
                boardCount.updateRecordDateAndResetWeekClickCount();
            }
            boardCount.plusCount();
            BoardCount boardCount1 = boardCountRepository.save(boardCount);
            System.out.println("Update = " + boardCount1);
        }
    }
}
