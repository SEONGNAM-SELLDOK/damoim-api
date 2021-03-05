package com.damoim.restapi.boards.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.restapi.boards.dao.BoardsRepository;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.model.ModifyBoardsRequest;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.damoim.restapi.config.DamoimFileUtil;
import lombok.RequiredArgsConstructor;

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

    public Long save(Board board) {
        boardsRepository.save(board);
        return board.getId();
    }

    @Transactional(readOnly = true)
    public Optional<Board> findById(Long id) {
        return boardsRepository.findById(id);
    }

    public Long modify(Long id, ModifyBoardsRequest request) {
        Address address = new Address(request.getCountry(), request.getCity(), request.getStreet());
        Optional<Board> seminar = boardsRepository.findById(id);
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
