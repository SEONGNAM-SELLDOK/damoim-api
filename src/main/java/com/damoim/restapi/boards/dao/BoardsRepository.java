package com.damoim.restapi.boards.dao;

import com.damoim.restapi.boards.entity.Boards;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

public interface BoardsRepository extends JpaRepository<Boards, Long> {

}
