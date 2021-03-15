package com.damoim.restapi.reply.contoller;

import com.damoim.restapi.reply.model.request.RequestDeleteReply;
import com.damoim.restapi.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @DeleteMapping("/reply")
    public ResponseEntity<Object> deleteReply(@RequestBody RequestDeleteReply requestDeleteReply) {
        replyService.deleteReply(requestDeleteReply);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
