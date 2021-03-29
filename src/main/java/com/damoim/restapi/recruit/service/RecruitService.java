package com.damoim.restapi.recruit.service;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.member.model.AuthUser;
import com.damoim.restapi.recruit.dao.*;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.recruit.model.*;
import com.damoim.restapi.reply.entity.Reply;
import com.damoim.restapi.reply.service.ReplyService;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
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
    private final ReplyService replyService;

    public RecruitResponse save(@Valid RecruitSaveRequest recruitSaveRequest, RequestFile file) {
        String fileName = null;
        if (Objects.nonNull(file) && file.nonNull()) {
            fileName = fileUtil.upload(file);
        }
        recruitSaveRequest.setImage(fileName);
        return responseMapper.toDto(repository.save(saveRequestMapper.toEntity(recruitSaveRequest)));
    }

    @Transactional(readOnly = true)
    public RecruitResponse getById(long id) {
        return responseMapper.toDto(getRecruitById(id));
    }

    public RecruitResponse update(@Valid RecruitUpdateRequest recruitUpdateRequest, RequestFile file, AuthUser authUser) {
        Recruit origin = getRecruitById(recruitUpdateRequest.getId());
        Recruit updateRecruit = updateRequestMapper.toEntity(recruitUpdateRequest);
        validateEditor(origin, authUser);
        String fileName = null;
        if (Objects.nonNull(file) && file.nonNull()) {
            fileName = fileUtil.upload(file);
        }
        updateRecruit.setImage(fileName);
        return responseMapper.toDto(repository.save(updateRecruit));
    }

    public void delete(long id, AuthUser authUser) {
        Recruit recruit = getRecruitById(id);
        validateEditor(recruit, authUser);
        repository.delete(recruit);
    }

    private void validateEditor(Recruit recruit, AuthUser editor) {
        if (!recruit.isRegister(editor.getEmail())) {
            throw new RuntimeException();
        }
    }

    private Recruit getRecruitById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundResource(HttpStatus.NOT_FOUND.toString(), String.valueOf(id)));
    }

    @Transactional(readOnly = true)
    public Set<RecruitResponse> getRecruitByCondition(Pageable pageable, RecruitGetRequest recruitGetRequest) {
        return recruitResponseSet(repositorySupport.search(recruitGetRequest, pageable));
    }

    private Set<RecruitResponse> recruitResponseSet(Set<Recruit> recruitSet) {
        return recruitSet.stream().map(responseMapper::toDto).collect(Collectors.toSet());
    }

    public RecruitResponseWithReply getRecruitIncludeReply(Long id, BoardType boardType) {
        RecruitResponse recruitResponse = getById(id);
        List<Reply> replyList = replyService.getReplyList(boardType, id);
        return new RecruitResponseWithReply(recruitResponse, replyList);
    }
}
