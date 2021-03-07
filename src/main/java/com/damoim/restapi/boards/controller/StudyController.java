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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * @author gisung go
 * @since 2021-03-03
 * */

@Slf4j
@Controller
@RequestMapping("study")
@RequiredArgsConstructor
public class StudyController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @PostMapping
    public ResponseEntity<ReadBoardsResponse> saveSeminar(final @Valid @RequestBody SaveBoardRequest request) {
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

        ReadBoardsResponse response = boardService.save(board);
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
    public ResponseEntity delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("pages")
    public ResponseEntity list(BoardSearchCondition condition, Pageable pageable) {
        condition.setBoardType(BoardType.STUDY);
        Page<ListBoardsResponse> listBoardsResponses = boardRepository.searchBoard(condition, pageable);
        return ResponseEntity.ok(listBoardsResponses);
    }

    @PostMapping("files") // 파일 등록하기
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttr) {
        String fileName = boardService.saveUploadFile(file);
        redirectAttr.addFlashAttribute("pictures", file);

        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);

        return ResponseEntity.ok(map);
    }
}
