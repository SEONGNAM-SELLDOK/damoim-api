package com.damoim.restapi.boards.controller;

import com.damoim.restapi.boards.dao.BoardsRepository;
import com.damoim.restapi.boards.dao.BoardsSearchCondition;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.entity.Boards;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.boards.model.ListBoardsResponse;
import com.damoim.restapi.boards.model.ModifyBoardsRequest;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.damoim.restapi.boards.model.SaveBoardRequest;
import com.damoim.restapi.boards.service.BoardsService;
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
@RequestMapping("seminar")
@RequiredArgsConstructor
public class SeminarController {
    private final BoardsService boardsService;
    private final BoardsRepository boardsRepository;

    @PostMapping
    public ResponseEntity<String> saveSeminar(final @Valid @RequestBody SaveBoardRequest request) {
        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());
        DamoimTag damoimTag = new DamoimTag(request.getDamoimTag());
        Boards boards = Boards.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .image(request.getImage())
                .address(address)
                .totalMember(request.getTotalMember())
                .currentMember(request.getCurrentMember())
                .subject(request.getSubject())
                .damoimTag(damoimTag)
                .endDate(request.getEndDate())
                .boardType(BoardType.SEMINAR)
                .build();

        Long seminarId = boardsService.save(boards);

        HashMap<String, Long> map = new HashMap<>();
        map.put("seminar_id", seminarId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<List<ReadBoardsResponse>> findById(@PathVariable("id") Long id) {
        List<ReadBoardsResponse> boardInfo = boardsService.findBoardInfo(id, BoardType.SEMINAR);
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
    public ResponseEntity list(BoardsSearchCondition condition, Pageable pageable) {
        Page<ListBoardsResponse> listBoardsResponses = boardsRepository.searchBoard(condition, pageable);
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
