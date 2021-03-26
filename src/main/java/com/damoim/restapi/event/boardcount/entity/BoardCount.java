package com.damoim.restapi.event.boardcount.entity;

import com.damoim.restapi.boards.entity.BoardType;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class BoardCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Builder.Default
    private Long clickCount = 1L;

    private LocalDate recordDate;

    @Builder.Default
    private Long weekClickCount = 1L;


    public void plusCount() {
        clickCount += 1;
        weekClickCount += 1;
    }

    public boolean isOneWeekAgo() {
        LocalDate plusWeeks = recordDate.plusWeeks(1);
        return LocalDate.now().isAfter(plusWeeks);
    }

    public void updateRecordDateAndResetWeekClickCount() {
        recordDate = LocalDate.now();
        weekClickCount = 0L;
    }
}
