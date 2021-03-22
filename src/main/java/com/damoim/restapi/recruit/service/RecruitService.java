package com.damoim.restapi.recruit.service;

import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.recruit.dao.*;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitGetRequest;
import com.damoim.restapi.recruit.model.RecruitResponse;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.model.RecruitUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@RequiredArgsConstructor
@Validated
@Transactional
@Service
public class RecruitService {

    private final RecruitRepository repository;
    private final RecruitSaveRequestMapper saveRequestMapper;
    private final RecruitUpdateRequestMapper updateRequestMapper;
    private final DamoimFileUtil fileUtil;
    private final RecruitRepositorySupport repositorySupport;
    private final RecruitResponseMapper responseMapper;

    public RecruitResponse save(@Valid RecruitSaveRequest recruitSaveRequest, MultipartFile file) {
        String fileName = file == null ? null : fileUtil.upload(file);
        recruitSaveRequest.setImage(fileName);
        return responseMapper.toDto(repository.save(saveRequestMapper.toEntity(recruitSaveRequest)));
    }

    @Transactional(readOnly = true)
    public RecruitResponse getById(@Valid Long id) {
        return responseMapper.toDto(repository.findById(id).orElseThrow(RuntimeException::new));
    }

    public RecruitResponse update(@Valid RecruitUpdateRequest recruitUpdateRequest, MultipartFile file) {
        String fileName = file == null ? null : fileUtil.upload(file);
        recruitUpdateRequest.setImage(fileName);
        return responseMapper.toDto(repository.save(updateRequestMapper.toEntity(recruitUpdateRequest)));
    }

    public void delete(@Valid Long id) {
        Recruit recruit = repository.findById(id).orElseThrow(RuntimeException::new);
        repository.delete(recruit);
    }

    @Transactional(readOnly = true)
    public Set<RecruitResponse> getRecruitByCondition(Pageable pageable, RecruitGetRequest recruitGetRequest) {
        return recruitResponseSet(repositorySupport.search(recruitGetRequest, pageable));
    }

    private Set<RecruitResponse> recruitResponseSet(Set<Recruit> recruitSet){
        return recruitSet.stream().map(responseMapper::toDto).collect(Collectors.toSet());
    }
}
