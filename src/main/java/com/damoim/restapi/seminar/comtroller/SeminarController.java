package com.damoim.restapi.seminar.comtroller;

import com.damoim.restapi.seminar.service.SeminarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

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

    @PostMapping("fileUpload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttr) {
        String fileName = seminarService.saveUploadFile(file);
        redirectAttr.addFlashAttribute("pictures", file);

        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);

        return new ResponseEntity(map, HttpStatus.OK);
    }
}
