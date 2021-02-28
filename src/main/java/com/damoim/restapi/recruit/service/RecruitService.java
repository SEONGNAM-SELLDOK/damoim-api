package com.damoim.restapi.recruit.service;

import com.damoim.restapi.recruit.dao.RecruitRepository;
import com.damoim.restapi.recruit.dao.RecruitSaveRequestMapper;
import com.damoim.restapi.recruit.dao.RecruitUpdateRequestMapper;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.RecruitSaveRequest;
import com.damoim.restapi.recruit.model.RecruitUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Recruit save(@Valid RecruitSaveRequest recruitSaveRequest) {
        return repository.save(saveRequestMapper.toEntity(recruitSaveRequest));
    }

    @Transactional(readOnly = true)
    public Recruit getById(@Valid Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Recruit update(@Valid RecruitUpdateRequest recruitUpdateRequest) {
        return repository.save(updateRequestMapper.toEntity(recruitUpdateRequest));
    }

    public void delete(@Valid Long id) {
        repository.delete(getById(id));
    }

    public Set<Recruit> getRecruitByCondition(Pageable pageable, Predicate<Recruit> lookUpValue) {
        Page<Recruit> recruitPage = repository.findAll(pageable);
        if(Objects.isNull(lookUpValue)){
            return recruitPage.toSet();
        }
        return lookUpRecruit(recruitPage.stream(), lookUpValue);
    }

    private Set<Recruit> lookUpRecruit(Stream<Recruit> recruitStream, Predicate<Recruit> lookUpValue) {
        return recruitStream.filter(lookUpValue).collect(Collectors.toSet());
    }
}
