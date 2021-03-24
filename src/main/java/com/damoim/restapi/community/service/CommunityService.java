package com.damoim.restapi.community.service;


import com.damoim.restapi.community.dao.CommunityRepository;
import com.damoim.restapi.community.entity.Community;
import com.damoim.restapi.community.model.ModifyCommunityRequest;
import com.damoim.restapi.community.model.ReadCommunityResponse;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author gisung go
 * @since 2021.03.18
 * */

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final ModelMapper modelMapper;

    public ReadCommunityResponse save(Community community) {
        communityRepository.save(community);
        return modelMapper.map(community, ReadCommunityResponse.class);
    }

    @Transactional(readOnly = true)
    public Community findById(Long id) {
        Optional<Community> communityId = communityRepository.findById(id);
        return communityId.orElseThrow(() -> new NotFoundResource(
                HttpStatus.NOT_FOUND.toString(),
                String.valueOf(communityId)
        ));
    }

    public Community modify(Long id, ModifyCommunityRequest request) {
        Community communityId = findById(id);
        Community community = communityRepository.save(request.updateTo(communityId));
        return modelMapper.map(community, Community.class);
    }

    public Long delete(Long id) {
        Community communityId = findById(id);
        communityRepository.deleteById(communityId.getId());
        return id;
    }

    public List<ReadCommunityResponse> findCommunityInfo(Long id) {
        return communityRepository.findByCommunityInfo(id);
    }
}
