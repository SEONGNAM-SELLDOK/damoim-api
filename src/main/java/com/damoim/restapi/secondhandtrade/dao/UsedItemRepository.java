package com.damoim.restapi.secondhandtrade.dao;

import com.damoim.restapi.secondhandtrade.entity.useditem.UsedItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsedItemRepository extends JpaRepository<UsedItem, Long> {


  @Query(value = "SELECT p FROM UsedItem p WHERE p.title LIKE %:title% OR p.description LIKE %:description%",
      countQuery = "SELECT COUNT(p.no) FROM UsedItem p WHERE p.title LIKE %:title%  OR p.description LIKE %:description%")
  Page<UsedItem> findAllSearch(String title, String description, Pageable pageable);

}
