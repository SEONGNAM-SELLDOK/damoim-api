package com.damoim.restapi.boards.service;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.boards.model.ReadBoardsResponse;
import com.damoim.restapi.boards.model.SaveBoardRequest;
import com.damoim.restapi.config.DamoimFileUtil;
import com.damoim.restapi.boards.dao.BoardsRepository;
import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Boards;
import com.damoim.restapi.boards.model.ModifyBoardsRequest;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final ModelMapper modelMapper;

    public ReadBoardsResponse save(Boards boards) {
        boardsRepository.save(boards);
        return modelMapper.map(boards, ReadBoardsResponse.class);
    }

    @Transactional(readOnly = true)
    public Boards findById(Long id) {
        Optional<Boards> Id = boardsRepository.findById(id);
        return Id .orElseThrow(() -> new NotFoundPage(
                HttpStatus.NOT_FOUND.toString(),
                String.valueOf(Id))
        );
    }

    public Boards modify(Long id, ModifyBoardsRequest request) {
        Boards boardId = findById(id);
        Boards board = boardsRepository.save(request.updateTo(boardId));
        return modelMapper.map(board, Boards.class);
    }

    public void delete(Long id) {
        boardsRepository.deleteById(id);
    }

    public List<ReadBoardsResponse> findBoardInfo(Long id, BoardType type) {
        return boardsRepository.findByBoardInfo(id, type);
    }

    public String saveUploadFile(MultipartFile upload_file) { return damoimFileUtil.upload(upload_file); }
}
