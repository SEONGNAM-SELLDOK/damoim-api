package com.damoim.restapi.like.service;

import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.service.BoardService;
import com.damoim.restapi.like.entity.BoardLike;
import com.damoim.restapi.like.model.ChangeLikeRequest;
import com.damoim.restapi.like.dao.BoardLikeRepository;
import com.damoim.restapi.like.model.ReadLikeResponse;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author gisung go
 * @since 2021-03-13
 */

@Service
@Transactional
@RequiredArgsConstructor
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardService boardService;
    private final ModelMapper modelMapper;

    public Optional<BoardLike> findById(Long id) {
        return boardLikeRepository.findById(id);
    }

    public ReadLikeResponse saveLike(BoardLike boardLike) {
        boardLikeRepository.save(boardLike);
        return modelMapper.map(boardLike, ReadLikeResponse.class);
    }

    public BoardLike changeLike(Member member, ChangeLikeRequest request) {
        Board board = boardService.findById(request.getBoardId());
        Long boardId = board.getId();

        String afterLike;
        if (request.getBoardLike().equals("1")) {
            afterLike = "0";
            boardLikeRepository.subtractLikeCount(boardId);
        } else {
            afterLike = "1";
            boardLikeRepository.addLikeCount(boardId);
        }
        BoardLike like = boardLikeRepository.findById(request.getBoardLikeId()).orElseThrow(() -> new NotFoundPage(
                HttpStatus.NOT_FOUND.toString(),
                String.valueOf(request.getBoardLikeId())));

        Long id = like.getId();
        BoardLike boardLike = boardLikeRepository.getBoardLikeInfo(id, boardId);

        System.out.println("id = " + boardLike.getBoardCount());

        return boardLike;
    }
}
