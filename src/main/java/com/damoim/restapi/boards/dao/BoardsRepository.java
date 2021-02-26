package com.damoim.restapi.boards.dao;

import com.damoim.restapi.boards.entity.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

@Repository
public interface BoardsRepository extends JpaRepository<Boards, Long>, BoardsRepositoryCustom {

}
