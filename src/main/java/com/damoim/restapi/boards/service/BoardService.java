package com.damoim.restapi.boards.service;

import java.util.List;
import java.util.Optional;

import com.damoim.restapi.secondhandtrade.errormsg.NotFoundPage;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.damoim.restapi.boards.dao.BoardRepository;
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
public class BoardService {
    private final BoardRepository boardRepository;
    private final DamoimFileUtil damoimFileUtil;
    private final ModelMapper modelMapper;

    public ReadBoardsResponse save(Board board) {
        boardRepository.save(board);
        return modelMapper.map(board, ReadBoardsResponse.class);
    }

    @Transactional(readOnly = true)
    public Board findById(Long id) {
        Optional<Board> Id = boardRepository.findById(id);
        return Id.orElseThrow(() -> new NotFoundPage(
                HttpStatus.NOT_FOUND.toString(),
                String.valueOf(Id))
        );
    }

    public Board modify(Long id, ModifyBoardsRequest request) {
        Board boardId = findById(id);
        Board board = boardRepository.save(request.updateTo(boardId));
        return modelMapper.map(board, Board.class);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public List<ReadBoardsResponse> findBoardInfo(Long id, BoardType type) {
        return boardRepository.findByBoardInfo(id, type);
    }

    public String saveUploadFile(MultipartFile upload_file) { return damoimFileUtil.upload(upload_file); }
}
