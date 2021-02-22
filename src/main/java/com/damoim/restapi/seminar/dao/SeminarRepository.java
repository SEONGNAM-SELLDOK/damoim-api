package com.damoim.restapi.seminar.dao;

import com.damoim.restapi.seminar.entity.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

public interface SeminarRepository extends JpaRepository<Seminar, Long> {

}
