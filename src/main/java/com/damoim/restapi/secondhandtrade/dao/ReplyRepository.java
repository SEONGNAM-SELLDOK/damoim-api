package com.damoim.restapi.secondhandtrade.dao;

import com.damoim.restapi.secondhandtrade.entity.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
