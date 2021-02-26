package com.damoim.restapi.secondhandtrade.dao;

import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedItemRepository extends JpaRepository<UsedItem, Long> {
}
