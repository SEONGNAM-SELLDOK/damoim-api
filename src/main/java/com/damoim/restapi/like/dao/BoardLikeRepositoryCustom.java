package com.damoim.restapi.like.dao;

import com.damoim.restapi.boards.entity.BoardType;
import com.damoim.restapi.like.entity.BoardLike;
import com.damoim.restapi.like.entity.LikeStatus;
import com.damoim.restapi.like.model.ChangeLikeRequest;
import com.damoim.restapi.like.model.ReadLikeResponse;
import com.damoim.restapi.like.model.SaveLikeRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author gisung.go
 * @since 2021-03-20
 */
public interface BoardLikeRepositoryCustom {
    List<ReadLikeResponse> findByLikeInfo(Long boardId, BoardType type);
//    List<ReadLikeResponse> changeLike(ChangeLikeRequest request);
}
