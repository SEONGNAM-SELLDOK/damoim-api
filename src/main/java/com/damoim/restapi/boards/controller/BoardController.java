package com.damoim.restapi.boards.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.damoim.restapi.boards.service.BoardsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Slf4j
@Api(value = "boards", tags = "보드 관련 REST API")
@Controller
@RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardsService boardsService;
    private final BoardRepository boardRepository;

    @PostMapping // type: SEMINAR, STUDY
    public ResponseEntity<String> save(final @Valid @RequestBody SaveBoardRequest request,
        @RequestParam(value = "type") BoardType type) {
        log.info(request.toString());

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
            .boardType(type)
                .build();

        Long seminarId = boardsService.save(board);

        HashMap<String, Long> map = new HashMap<>();
        map.put("seminar_id", seminarId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<List<ReadBoardsResponse>> findById(@PathVariable("id") Long id, @RequestParam(value = "type") BoardType type) {
        List<ReadBoardsResponse> boardInfo = boardsService.findBoardInfo(id, type);
        return ResponseEntity.ok(boardInfo);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<String> modify(@PathVariable("id") Long id, final @Valid @RequestBody ModifyBoardsRequest request) {
        Long seminarId = boardsService.modify(id, request);
        HashMap<String, Long> map = new HashMap<>();
        map.put("board_id", seminarId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        boardsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("pages")
    public ResponseEntity list(BoardSearchCondition condition, Pageable pageable) {
        Page<ListBoardsResponse> listBoardsResponses = boardRepository.searchBoard(condition, pageable);
        return ResponseEntity.ok(listBoardsResponses);
    }

    @PostMapping("files") // 파일 등록하기
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttr) {
        String fileName = boardsService.saveUploadFile(file);
        redirectAttr.addFlashAttribute("pictures", file);

        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);

        return ResponseEntity.ok(map);
    }
}
