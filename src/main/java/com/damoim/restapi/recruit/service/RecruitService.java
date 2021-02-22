package com.damoim.restapi.recruit.service;

import com.damoim.restapi.recruit.dao.RecruitRepository;
import com.damoim.restapi.recruit.dao.RecruitSaveRequestDtoMapper;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@RequiredArgsConstructor
@Transactional
@Service
public class RecruitService {

    private final RecruitRepository repository;
    private final RecruitSaveRequestDtoMapper saveRequestDtoMapper;

    public Recruit save(RecruitSaveRequestDto recruitSaveRequestDto) {
        return repository.save(saveRequestDtoMapper.toEntity(recruitSaveRequestDto));
    }

}
