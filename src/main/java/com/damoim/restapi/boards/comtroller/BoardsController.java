package com.damoim.restapi.boards.comtroller;

import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.entity.Boards;
import com.damoim.restapi.boards.entity.DamoimTag;
import com.damoim.restapi.boards.model.ModifySeminarRequest;
import com.damoim.restapi.boards.model.ReadSeminarResponse;
import com.damoim.restapi.boards.model.SaveBoardRequest;
import com.damoim.restapi.boards.service.BoardsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @since 2021-02-22
 * */
@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardsController {

    private final BoardsService boardsService;

    @PostMapping
    public ResponseEntity<String> save(final @Valid @RequestBody SaveBoardRequest request,
                                       @RequestParam(value = "type") BoardType type) {
        log.info(request.toString());

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
                .boardType(type)
                .build();

        Long seminarId = boardsService.save(boards);

        HashMap<String, Long> map = new HashMap<>();
        map.put("seminar_id", seminarId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<ReadSeminarResponse> findById(@PathVariable("id") Long id) {
        return new ResponseEntity(boardsService.findById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @ResponseBody // 세미나 수정하기
    public ResponseEntity<String> modify(@PathVariable("id") Long id, final @Valid @RequestBody ModifySeminarRequest request) {
        Long seminarId = boardsService.modify(id, request);
        HashMap<String, Long> map = new HashMap<>();
        map.put("seminar_id", seminarId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseBody // 세미나 삭제하기
    public ResponseEntity delete(@PathVariable("id") Long id) {
        boardsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("search")
//    public ResponseEntity<List<ReadSeminarResponse>> searchPage(ReadSeminarResponse response) {
//        List<Boards> seminarsInfo = boardsService.getList(response);
//        return new ResponseEntity(seminarsInfo, HttpStatus.OK);
//    }

    @PostMapping("files") // 파일 등록하기
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttr) {
        String fileName = boardsService.saveUploadFile(file);
        redirectAttr.addFlashAttribute("pictures", file);

        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);

        return ResponseEntity.ok(map);
    }
}
