package com.damoim.restapi.recruit.service;

import com.damoim.restapi.recruit.dao.RecruitRepository;
import com.damoim.restapi.recruit.dao.RecruitSaveRequestMapper;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
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
@Transactional
@Validated
@Service
public class RecruitService {

    private final RecruitRepository repository;
    private final RecruitSaveRequestMapper saveRequestDtoMapper;

    public Recruit save(@Valid RecruitSaveRequest recruitSaveRequest) {
        return repository.save(saveRequestDtoMapper.toEntity(recruitSaveRequest));
    }

}
