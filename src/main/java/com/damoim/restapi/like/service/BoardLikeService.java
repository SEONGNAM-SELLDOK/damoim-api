package com.damoim.restapi.like.service;

import com.damoim.restapi.like.entity.BoardLike;
import com.damoim.restapi.like.model.ChangeLikeRequest;
import com.damoim.restapi.like.dao.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
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

    public String changeLike(ChangeLikeRequest request) {
        Optional<BoardLike> boardId = Optional.ofNullable(boardLikeRepository.getOne(request.getId()));
        String like = request.getBoardLike();

        String boardLike;
        if (like.equals("1")) {
            boardLike = "0";
            boardLikeRepository.subtractLikeCount(request.getId());
        } else {
            boardLike = "1";
            boardLikeRepository.addLikeCount(request.getId());
        }
//        boardLikeRepository.changeLike(request.getId(), boardLike);

        return boardLike;
    }
}
