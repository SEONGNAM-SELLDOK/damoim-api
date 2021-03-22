package com.damoim.restapi.like.service;

import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.service.BoardService;
import com.damoim.restapi.like.dao.LikeStatusRepository;
import com.damoim.restapi.like.entity.BoardLike;
import com.damoim.restapi.like.entity.LikeStatus;
import com.damoim.restapi.like.model.ChangeLikeRequest;
import com.damoim.restapi.like.dao.BoardLikeRepository;
import com.damoim.restapi.like.model.ReadLikeResponse;
import com.damoim.restapi.like.model.SaveLikeRequest;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final LikeStatusRepository likeStatusRepository;
    private final MemberService memberService;
    private final ModelMapper modelMapper;

    public Optional<BoardLike> findById(Long id) {
        return boardLikeRepository.findById(id);
    }

    public SaveLikeRequest saveLike(BoardType boardType, Long boardId, AuthUser member) {
        Member byName = memberService.findByName(member.getEmail());
        System.out.println("byName = " + byName);
        // BoardLike 저장
        BoardLike boardLike = BoardLike.builder()
                .boardId(boardId)
                .boardType(boardType)
                .boardCount(0)
                .build();
        BoardLike save = boardLikeRepository.save(boardLike);

        // LikeStatus 저장
        LikeStatus likeStatus = LikeStatus.builder()
                .boardLike(boardLike)
                .memberLike(byName)
                .status(false)
                .build();
        LikeStatus lSave = likeStatusRepository.save(likeStatus);


        SaveLikeRequest request = new SaveLikeRequest(save.getBoardId(), save.getId(), lSave.getStatus());

        return modelMapper.map(request, SaveLikeRequest.class);
    }

    public List<ReadLikeResponse> findByLikeInfo(Long boardId, BoardType type) {
        return boardLikeRepository.findByLikeInfo(boardId, type);
    }

    public ReadLikeResponse changeLike(ChangeLikeRequest request) {
        Board board = boardService.findById(request.getBoardId());

        Long boardId = board.getId();
        Long likeStatusId = request.getBoardLikeId();
        Boolean status = request.getStatus();

        if (request.getStatus().equals(true)) {
            boardLikeRepository.addLikeCount(boardId);
            likeStatusRepository.updateStatusTrue(status, likeStatusId);
            status = true;
        } else {
            boardLikeRepository.subtractLikeCount(boardId);
            likeStatusRepository.updateStatusFalse(status, likeStatusId);
            status = false;
        }
        int boardCount = boardLikeRepository.getBoardCount(boardId);
        return new ReadLikeResponse(boardId, status, boardCount);
    }
}
