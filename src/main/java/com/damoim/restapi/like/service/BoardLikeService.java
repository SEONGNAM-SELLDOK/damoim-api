package com.damoim.restapi.like.service;

import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.service.BoardService;
import com.damoim.restapi.like.entity.BoardLike;
import com.damoim.restapi.like.model.ChangeLikeRequest;
import com.damoim.restapi.like.dao.BoardLikeRepository;
import com.damoim.restapi.like.model.ReadLikeResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    public String changeLike(ChangeLikeRequest request) {
        Board boardId = boardService.findById(request.getBoardId());
        System.out.println("boardId = " + boardId);
        String boardLike = request.getBoardLike();

//        String like;
        if (boardLike.equals("1")) {
//            boardLike = "0";
            boardLikeRepository.subtractLikeCount(boardId.getId());
        } else {
//            boardLike = "1";
            boardLikeRepository.addLikeCount(boardId.getId());
        }
//        boardLikeRepository.changeLike(request.getId(), boardLike);

        return boardLike;
    }
}
