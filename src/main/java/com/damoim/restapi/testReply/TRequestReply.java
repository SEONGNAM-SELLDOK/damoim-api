package com.damoim.restapi.testReply;


import com.damoim.restapi.boards.entity.BoardType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRequestReply {

    @Min(1)
    private Long no;

    @NotEmpty
    private String writer;

    @NotEmpty
    private String content;

    private BoardType boardType;

    public TReply toEntity() {
        return TReply.builder()
            .level(1)
            .writer(writer)
            .boardType(boardType)
            .content(content)
            .build();
    }

    public TRequestReply checkUrl(String url) {
        for (BoardType value : BoardType.values()) {
            if (value.name().equals(url.toUpperCase())) {
                this.boardType = BoardType.valueOf(url.toUpperCase());
                return this;
            }
        }
        throw new IllegalArgumentException();
    }
}
