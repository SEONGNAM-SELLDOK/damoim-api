package com.damoim.restapi.lecture.service;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.bookreview.exception.UnauthorizedException;
import com.damoim.restapi.config.fileutil.DamoimFileUtil;
import com.damoim.restapi.config.fileutil.model.RequestFile;
import com.damoim.restapi.lecture.dao.*;
import com.damoim.restapi.lecture.entity.Lecture;
import com.damoim.restapi.lecture.model.LectureGetRequest;
import com.damoim.restapi.lecture.model.LectureResponse;
import com.damoim.restapi.lecture.model.LectureSaveRequest;
import com.damoim.restapi.lecture.model.LectureUpdateRequest;
import com.damoim.restapi.member.model.AuthUser;
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
 * @author leekyunghee
 * @since 2021. 02. 25
 */

@Validated
@Transactional
@RequiredArgsConstructor
@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LectureSaveRequestMapper saveRequestMapper;
    private final LectureUpdateRequestMapper updateRequestMapper;
    private final DamoimFileUtil fileUtil;
    private final LectureRepositorySupport repositorySupport;
    private final LectureResponseMapper responseMapper;
    private final ReplyService replyService;

    public LectureResponse save(@Valid LectureSaveRequest request, RequestFile file) {
        String imageUrl = null;
        if (Objects.nonNull(file) && file.nonNull()) {
            imageUrl = fileUtil.upload(file);
        }
        Lecture lecture = saveRequestMapper.toEntity(request);
        lecture.setImage(imageUrl);
        return responseMapper.toDto(lectureRepository.save(lecture));
    }

    @Transactional(readOnly = true)
    public LectureResponse findById(long id) {
        return responseMapper.toDto(lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Lecture not found (id = %s)", id))));
    }

    public void delete(long id, AuthUser authUser) {
        validateEditor(getLectureById(id), authUser);
        lectureRepository.deleteById(id);
    }

    public LectureResponse update(@Valid LectureUpdateRequest updateRequest, RequestFile file, AuthUser authUser) {
        Lecture origin = getLectureById(updateRequest.getId());
        validateEditor(origin, authUser);
        Lecture updateLecture = updateRequestMapper.toEntity(updateRequest);
        origin.update(updateLecture);
        String imageUrl = null;
        if (Objects.nonNull(file) && file.nonNull()) {
            imageUrl = fileUtil.upload(file);
        }
        origin.setImage(imageUrl);
        return responseMapper.toDto(lectureRepository.saveAndFlush(origin));
    }

    private void validateEditor(Lecture lecture, AuthUser authUser) {
        if (Objects.isNull(lecture) || Objects.isNull(authUser)) {
            throw new IllegalArgumentException();
        }
        if (!lecture.isRegister(authUser.getEmail())) {
            throw new UnauthorizedException("작성자가 아닙니다.");
        }
    }

    private Lecture getLectureById(long id) {
        return lectureRepository.findById(id).orElseThrow(() -> new NotFoundResource(HttpStatus.NOT_FOUND.toString(), String.valueOf(id)));
    }

    public Set<LectureResponse> getLectureByCondition(Pageable pageable, LectureGetRequest getRequest) {
        return lectureResponseSet(repositorySupport.search(getRequest, pageable));
    }

    private Set<LectureResponse> lectureResponseSet(Set<Lecture> lectureSet) {
        return lectureSet.stream().map(responseMapper::toDto).collect(Collectors.toSet());
    }

    public LectureResponseWithReply getLectureIncludeReply(long id, BoardType boardType) {
        LectureResponse lectureResponse = findById(id);
        List<Reply> replyList = replyService.getReplyList(boardType, id);
        return new LectureResponseWithReply(lectureResponse, replyList);
    }
}
