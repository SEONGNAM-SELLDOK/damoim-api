package com.damoim.restapi.boards.controller;

import java.util.List;

import javax.validation.Valid;

import com.damoim.restapi.boards.model.*;
import com.damoim.restapi.like.model.ReadLikeResponse;
import com.damoim.restapi.like.service.BoardLikeService;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.restapi.boards.dao.BoardRepository;
import com.damoim.restapi.boards.dao.BoardSearchCondition;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.boards.service.BoardService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gisung go
 * @since 2021-03-03
 * */

@Slf4j
@Api(value = "boards", tags = "보드 관련 REST API")
@Controller
@RequestMapping("seminar")
@RequiredArgsConstructor
public class SeminarController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final BoardLikeService boardLikeService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ReadBoardsResponse> saveSeminar(
        @AuthenticationPrincipal AuthUser member,
        final @Valid @RequestBody SaveBoardRequest request,
        @RequestParam(required = false) MultipartFile file) {

        Board board = Board.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .address(new Address(request.getCountry(), request.getCity(), request.getStreet()))
            .totalMember(request.getTotalMember())
            .currentMember(request.getCurrentMember())
            .subject(request.getSubject())
            .damoimTag(new DamoimTag(request.getDamoimTag()))
            .endDate(request.getEndDate())
            .boardType(BoardType.SEMINAR)
            .build();
        ReadBoardsResponse response = boardService.save(board, file);

        // 좋아요 등록
        boardLikeService.saveLike(BoardType.SEMINAR, response.getId(), member);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ReadBoardsIncludeLikeResponse findById(@PathVariable("id") Long id) {
        List<ReadBoardsResponse> seminarInfo = boardService.findBoardInfo(id, BoardType.SEMINAR);
        List<ReadLikeResponse> byLikeInfo = boardLikeService.findByLikeInfo(id, BoardType.SEMINAR);

        return ReadBoardsIncludeLikeResponse.toMapper(seminarInfo, byLikeInfo);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<Board> modify(
        @PathVariable("id") Long id,
        final @Valid @RequestBody ModifyBoardsRequest request) {
        Board seminar = boardService.modify(id, request);
        return ResponseEntity.ok(seminar);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        Long boardId = boardService.delete(id);
        return ResponseEntity.ok(boardId);
    }



    @GetMapping("pages")
    public ResponseEntity<Page<ListBoardsResponse>> list(
            @AuthenticationPrincipal AuthUser member,
            BoardSearchCondition condition,
            Pageable pageable) {
        condition.setBoardType(BoardType.SEMINAR);
        Page<ListBoardsResponse> responses = boardRepository.searchBoard(condition, pageable);
        return ResponseEntity.ok(responses);
    }
}
