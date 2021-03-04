package com.damoim.restapi.boards.service;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.damoim.restapi.config.DamoimFileUtil;
import com.damoim.restapi.boards.dao.BoardsRepository;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Boards;
import com.damoim.restapi.boards.model.ModifyBoardsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Service
@Transactional
@RequiredArgsConstructor
public class BoardsService {
    private final BoardsRepository boardsRepository;
    private final DamoimFileUtil damoimFileUtil;

    public Long save(Boards boards) {
        boardsRepository.save(boards);
        return boards.getId();
    }

    @Transactional(readOnly = true)
    public Optional<Boards> findById(Long id) {
        return boardsRepository.findById(id);
    }

    public Long modify(Long id, ModifyBoardsRequest request) {
        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());
        Optional<Boards> seminar = boardsRepository.findById(id);
        seminar.ifPresent(existingSeminar -> {
            existingSeminar.setTitle(request.getTitle());
            existingSeminar.setContent(request.getContent());
            existingSeminar.setImage(request.getImage());
            existingSeminar.setAddress(address);
            existingSeminar.setSubject(request.getSubject());
            existingSeminar.setDamoimTag(request.getDamoimTag());
            existingSeminar.setEndDate(request.getEndDate());
            boardsRepository.save(existingSeminar);
        });

        return id;
    }

    public void delete(Long id) {
        boardsRepository.deleteById(id);
    }

    public List<ReadBoardsResponse> findBoardInfo(Long id, BoardType type) {
        return boardsRepository.findByBoardInfo(id, type);
    }


    public String saveUploadFile(MultipartFile upload_file) { return damoimFileUtil.upload(upload_file); }


}
