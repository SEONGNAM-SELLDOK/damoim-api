package com.damoim.restapi.recruit.service;

import com.damoim.restapi.recruit.dao.RecruitRepository;
import com.damoim.restapi.recruit.dao.RecruitSaveRequestMapper;
import com.damoim.restapi.recruit.dao.RecruitUpdateRequestMapper;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.model.RecruitUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@RequiredArgsConstructor
@Validated
@Service
public class RecruitService {

    private final RecruitRepository repository;
    private final RecruitSaveRequestMapper saveRequestMapper;
    private final RecruitUpdateRequestMapper updateRequestMapper;

    @Transactional
    public Recruit save(@Valid RecruitSaveRequest recruitSaveRequest) {
        return repository.save(saveRequestMapper.toEntity(recruitSaveRequest));
    }

    @Transactional
    public Recruit getRecruit(@Valid Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public Recruit updateRecruit(@Valid RecruitUpdateRequest recruitUpdateRequest) {
        return repository.save(updateRequestMapper.toEntity(recruitUpdateRequest));
    }

}
