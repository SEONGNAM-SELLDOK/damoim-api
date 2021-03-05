package com.damoim.restapi.boards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.damoim.restapi.boards.entity.Board;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

@Repository
public interface BoardsRepository extends JpaRepository<Board, Long>, BoardsRepositoryCustom {

}
