package com.damoim.restapi.introduce.service;

import com.damoim.restapi.introduce.dao.IntroRepository;
import com.damoim.restapi.introduce.dao.IntroResponseMapper;
import com.damoim.restapi.introduce.dao.IntroSaveRequestMapper;
import com.damoim.restapi.introduce.dao.IntroUpdateRequestMapper;
import com.damoim.restapi.introduce.entity.Introduce;
import com.damoim.restapi.introduce.model.IntroResponse;
import com.damoim.restapi.introduce.model.IntroSaveRequest;
import com.damoim.restapi.introduce.model.IntroUpdateRequest;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
@RequiredArgsConstructor
@Transactional
@Service
public class IntroService {

    private final IntroRepository repository;
    private final IntroSaveRequestMapper saveRequestMapper;
    private final IntroResponseMapper responseMapper;
    private final IntroUpdateRequestMapper updateRequestMapper;

    public IntroResponse create(@Valid IntroSaveRequest saveRequest) {
        return responseMapper.toDto(repository.save(saveRequestMapper.toEntity(saveRequest)));
    }

    @Transactional(readOnly = true)
    public IntroResponse getById(long id) {
        return responseMapper.toDto(getIntroduceById(id));
    }

    private Introduce getIntroduceById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundResource(HttpStatus.NOT_FOUND.toString(), String.valueOf(id)));
    }

    public IntroResponse update(@Valid IntroUpdateRequest updateRequest, AuthUser authUser) {
        Introduce origin = getIntroduceById(updateRequest.getId());
        validateEditor(origin, authUser);
        return responseMapper.toDto(repository.save(updateRequestMapper.toEntity(updateRequest)));
    }

    public void delete(long id, AuthUser authUser) {
        Introduce introduce = getIntroduceById(id);
        validateEditor(introduce, authUser);
        repository.delete(introduce);
    }

    private void validateEditor(Introduce introduce, AuthUser authUser) {
        if (Objects.isNull(introduce) || Objects.isNull(authUser) || !introduce.isRegister(authUser.getEmail())) {
            throw new RuntimeException();
        }
    }

    public List<IntroResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(responseMapper::toDto).collect(Collectors.toList());
    }
}
