package com.damoim.restapi.boards.controller;

import com.damoim.restapi.boards.dao.BoardRepository;
import com.damoim.restapi.boards.dao.BoardSearchCondition;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.boards.model.ListBoardsResponse;
import com.damoim.restapi.boards.model.ModifyBoardsRequest;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.damoim.restapi.boards.model.SaveBoardRequest;
import com.damoim.restapi.boards.service.BoardService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * @author gisung go
 * @since 2021-03-03
 */

@Slf4j
@Controller
@RequestMapping("study")
@RequiredArgsConstructor
public class StudyController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @PostMapping
    public ResponseEntity<ReadBoardsResponse> saveStudy(
        final @Valid @RequestBody SaveBoardRequest request,
        @RequestParam(required = false) MultipartFile file) {
        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());
        DamoimTag damoimTag = new DamoimTag(request.getDamoimTag());
        Board board = Board.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .image(request.getImage())
            .address(address)
            .totalMember(request.getTotalMember())
            .currentMember(request.getCurrentMember())
            .subject(request.getSubject())
            .damoimTag(damoimTag)
            .endDate(request.getEndDate())
            .boardType(BoardType.STUDY)
            .build();

        ReadBoardsResponse response = boardService.save(board, file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<List<ReadBoardsResponse>> findById(@PathVariable("id") Long id) {
        List<ReadBoardsResponse> boardInfo = boardService.findBoardInfo(id, BoardType.STUDY);
        return ResponseEntity.ok(boardInfo);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<Board> modify(
        @PathVariable("id") Long id,
        final @Valid @RequestBody ModifyBoardsRequest request) {
        Board boards = boardService.modify(id, request);
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        Long boardId = boardService.delete(id);
        return ResponseEntity.ok(boardId);
    }

    @GetMapping("pages")
    public ResponseEntity<Page<ListBoardsResponse>> list(BoardSearchCondition condition,
        Pageable pageable) {
        condition.setBoardType(BoardType.STUDY);
        Page<ListBoardsResponse> listBoardsResponses = boardRepository
            .searchBoard(condition, pageable);
        return ResponseEntity.ok(listBoardsResponses);
    }
}
