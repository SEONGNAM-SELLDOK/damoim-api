package com.damoim.restapi.seminar.comtroller;

import com.damoim.restapi.seminar.entity.Address;
import com.damoim.restapi.seminar.entity.Seminar;
import com.damoim.restapi.seminar.model.ModifySeminarRequest;
import com.damoim.restapi.seminar.model.ReadSeminarResponse;
import com.damoim.restapi.seminar.model.SaveSeminarRequest;
import com.damoim.restapi.seminar.service.SeminarService;
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
@RequestMapping("seminar")
@RequiredArgsConstructor
public class SeminarController {

    private final SeminarService seminarService;

    @PostMapping // 세미나 등록
    public ResponseEntity<String> save(final @Valid @RequestBody SaveSeminarRequest request) {
        log.info(request.toString());

        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());
        Seminar seminar = Seminar.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .image(request.getImage())
                .address(address)
                .totalMember(request.getTotalMember())
                .currentMember(request.getCurrentMember())
                .subject(request.getSubject())
                .damoimTag(request.getDamoimTag())
                .endDate(request.getEndDate())
                .build();

        Long seminarId = seminarService.save(seminar);

        HashMap<String, Long> map = new HashMap<>();
        map.put("seminar_id", seminarId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<ReadSeminarResponse> findById(@PathVariable("id") Long id) {
        return new ResponseEntity(seminarService.findById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @ResponseBody // 세미나 수정하기
    public ResponseEntity<String> modify(@PathVariable("id") Long id, final @Valid @RequestBody ModifySeminarRequest request) {
        Long seminarId = seminarService.modify(id, request);
        HashMap<String, Long> map = new HashMap<>();
        map.put("seminar_id", seminarId);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseBody // 세미나 삭제하기
    public ResponseEntity delete(@PathVariable("id") Long id) {
        seminarService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("search")
    public ResponseEntity<List<ReadSeminarResponse>> searchPage(ReadSeminarResponse response) {
        List<Seminar> seminarsInfo = seminarService.getList(response);
        return new ResponseEntity(seminarsInfo, HttpStatus.OK);
    }

    @PostMapping("fileUpload") // 파일 등록하기
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttr) {
        String fileName = seminarService.saveUploadFile(file);
        redirectAttr.addFlashAttribute("pictures", file);

        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);

        return new ResponseEntity(map, HttpStatus.OK);
    }
}
