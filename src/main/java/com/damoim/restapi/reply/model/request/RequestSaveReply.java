package com.damoim.restapi.reply.model.request;


import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.reply.entity.ChildReply;
import com.damoim.restapi.reply.entity.Reply;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSaveReply {

    @Min(1)
    private Long parentReplyId;

    @NotNull
    private Boolean isChildId;

    @NotEmpty
    private String writer;

    @NotEmpty
    private String content;


    private BoardType boardType;

    public boolean isParentReply() {
        return Objects.isNull(parentReplyId) && !isChildId;
    }

    public boolean isParentChildReply() {
        return Objects.nonNull(parentReplyId) && !isChildId;
    }

    public boolean isChildChildReply() {
        return Objects.nonNull(parentReplyId) && isChildId;
    }

    public Reply toReply(Long boardId) {
        return Reply.builder()
            .writer(writer)
            .boardId(boardId)
            .boardType(boardType)
            .content(content)
            .build();
    }

    public ChildReply toChildReply(Reply parentReply) {
        return ChildReply.builder()
            .content(content)
            .parentReply(parentReply)
            .writer(writer)
            .build();
    }

    public RequestSaveReply checkUrl(String url) {
        for (BoardType value : BoardType.values()) {
            if (value.name().equals(url.toUpperCase())) {
                this.boardType = BoardType.valueOf(url.toUpperCase());
                return this;
            }
        }
        throw new IllegalArgumentException();
    }
}
