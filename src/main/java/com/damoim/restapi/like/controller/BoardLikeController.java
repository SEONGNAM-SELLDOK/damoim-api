package com.damoim.restapi.like.controller;

import com.damoim.restapi.like.model.ChangeLikeRequest;
import com.damoim.restapi.like.service.BoardLikeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@Api(value = "boardLike", tags = "좋아요 기능 REST API")
@Controller
@RequestMapping("like")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @ResponseBody
    public ResponseEntity<String> changeLike(final @Valid @RequestBody ChangeLikeRequest request) {
        String like = boardLikeService.changeLike(request);

        HashMap<String, String> map = new HashMap<>();
        map.put("like", like);

        return new ResponseEntity(map, HttpStatus.OK);
    }
}
