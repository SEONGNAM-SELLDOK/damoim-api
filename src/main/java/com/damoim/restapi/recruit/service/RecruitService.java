package com.damoim.restapi.recruit.service;

import com.damoim.restapi.config.DamoimFileUtil;
import com.damoim.restapi.recruit.dao.RecruitRepository;
import com.damoim.restapi.recruit.dao.RecruitRepositorySupport;
import com.damoim.restapi.recruit.dao.RecruitSaveRequestMapper;
import com.damoim.restapi.recruit.dao.RecruitUpdateRequestMapper;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitGetRequest;
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

    public Recruit save(@Valid RecruitSaveRequest recruitSaveRequest, MultipartFile file) {
        String fileName = file == null ? null : fileUtil.upload(file);
        recruitSaveRequest.setImage(fileName);
        return repository.save(saveRequestMapper.toEntity(recruitSaveRequest));
    }

    @Transactional(readOnly = true)
    public Recruit getById(@Valid Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Recruit update(@Valid RecruitUpdateRequest recruitUpdateRequest, MultipartFile file) {
        String fileName = file == null ? null : fileUtil.upload(file);
        recruitUpdateRequest.setImage(fileName);
        return repository.save(updateRequestMapper.toEntity(recruitUpdateRequest));
    }

    public void delete(@Valid Long id) {
        repository.delete(getById(id));
    }

    @Transactional(readOnly = true)
    public Set<Recruit> getRecruitByCondition(Pageable pageable, RecruitGetRequest recruitGetRequest) {
        return repositorySupport.search(recruitGetRequest, pageable);
    }
}
