package com.damoim.restapi.community.controller;

import com.damoim.restapi.community.dao.CommunityRepository;
import com.damoim.restapi.community.dao.CommunitySearchCondition;
import com.damoim.restapi.community.entity.Community;
import com.damoim.restapi.community.model.ListCommunityResponse;
import com.damoim.restapi.community.model.ModifyCommunityRequest;
import com.damoim.restapi.community.model.ReadCommunityResponse;
import com.damoim.restapi.community.model.SaveCommunityRequest;
import com.damoim.restapi.community.service.CommunityService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author gisung go
 * @since 2021-03-18
 * */

@Slf4j
@Api(value = "community", tags = "커뮤니티 관련 REST API")
@Controller
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityRepository communityRepository;

    @PostMapping
    public ResponseEntity<ReadCommunityResponse> saveCommunity(final @Valid @RequestBody SaveCommunityRequest request) {
        Community community = Community.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        ReadCommunityResponse response = communityService.save(community);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<List<ReadCommunityResponse>> findCommunityInfo(@PathVariable("id") Long id) {
        List<ReadCommunityResponse> communityInfo = communityService.findCommunityInfo(id);
        return ResponseEntity.ok(communityInfo);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<Community> modify(
        @PathVariable("id") Long id,
        final @Valid @RequestBody ModifyCommunityRequest request) {
        Community modify = communityService.modify(id, request);
        return ResponseEntity.ok(modify);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        Long deleteId = communityService.delete(id);
        return ResponseEntity.ok(deleteId);
    }

    @GetMapping("pages")
    public ResponseEntity<Page<ListCommunityResponse>> list(
            CommunitySearchCondition condition,
            Pageable pageable) {
        Page<ListCommunityResponse> responses = communityRepository.searchCommunity(condition, pageable);

        return ResponseEntity.ok(responses);
    }

}
