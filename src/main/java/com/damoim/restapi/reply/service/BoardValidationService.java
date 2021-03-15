package com.damoim.restapi.reply.service;


import com.damoim.restapi.boards.dao.BoardRepository;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.recruit.dao.RecruitRepository;
import com.damoim.restapi.recruit.entity.Recruit;
import com.damoim.restapi.secondhandtrade.dao.UsedItemRepository;
import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import com.damoim.restapi.secondhandtrade.errormsg.NotFoundResource;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 13
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardValidationService {

    private final UsedItemRepository usedItemRepository;
    private final RecruitRepository recruitRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    public Long existBoard(Long boarId, BoardType boardType) {
        boolean result = false;
        if (BoardType.USEDITEMS.equals(boardType)) {
            result = usedItemExist(boarId);
        }

        if (BoardType.RECRUIT.equals(boardType)) {
            result = recruitExist(boarId);
        }

        if (BoardType.SEMINAR.equals(boardType) || BoardType.STUDY.equals(boardType)) {
            result = boardExist(boarId);
        }

        if (!result) {
            throw new NotFoundResource(HttpStatus.NOT_FOUND.toString(),
                String.valueOf(boarId));
        }
        return boarId;
    }

    public <T> T getEntity(Class<T> tClass, Long id) {
        if (tClass == UsedItem.class) {
            return modelMapper.map(getUsedItem(id), tClass);
        }
        if (tClass == Board.class) {
            return modelMapper.map(getBoard(id), tClass);
        }
        if (tClass == Recruit.class) {
            return modelMapper.map(getRecruit(id), tClass);
        }
        throw new IllegalArgumentException(tClass.getName());
    }


    private UsedItem getUsedItem(Long id) {
        return usedItemRepository.findById(id).orElseThrow(getFoundPageSupplier(id));
    }

    private Recruit getRecruit(Long id) {
        return recruitRepository.findById(id).orElseThrow(getFoundPageSupplier(id));
    }

    private Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(getFoundPageSupplier(id));
    }


    private boolean usedItemExist(Long id) {
        return usedItemRepository.existsById(id);
    }

    private boolean recruitExist(Long id) {
        return recruitRepository.existsById(id);
    }

    private boolean boardExist(Long id) {
        return boardRepository.existsById(id);
    }


    private Supplier<NotFoundResource> getFoundPageSupplier(Long id) {
        return () -> new NotFoundResource(HttpStatus.NOT_FOUND.toString(), String.valueOf(id));
    }

}
