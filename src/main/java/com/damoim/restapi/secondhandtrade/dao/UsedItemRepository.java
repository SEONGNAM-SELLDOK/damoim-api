package com.damoim.restapi.secondhandtrade.dao;

import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsedItemRepository extends JpaRepository<UsedItem, Long> {

    @Query("select u from UsedItem u left join fetch  u.replyList where u.no = :no")
    Optional<UsedItem> findByIdJoinFetch(Long no);

}
