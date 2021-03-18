package com.damoim.restapi.like.service;

import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.service.BoardService;
import com.damoim.restapi.like.entity.BoardLike;
import com.damoim.restapi.like.model.ChangeLikeRequest;
import com.damoim.restapi.like.dao.BoardLikeRepository;
import com.damoim.restapi.like.model.ReadLikeResponse;
import com.damoim.restapi.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    public HashMap<String, String> changeLike(Member member, ChangeLikeRequest request) {
        Board board = boardService.findById(request.getBoardId());
        Long boardId = board.getId();

        String likeResult;
        int i;
        if (request.getBoardLike().equals("1")) {
            likeResult = "0";
             i = boardLikeRepository.subtractLikeCount(boardId);
        } else {
            likeResult = "1";
            i = boardLikeRepository.addLikeCount(boardId);
        }

        int byBoardCount = boardLikeRepository.getBoardCount(boardId);

        HashMap<String, String> map = new HashMap<>();
        map.put("like_result", likeResult);
        map.put("board_count", String.valueOf(byBoardCount));

        return map;
    }
}
