package com.damoim.restapi.recruit.dao;

import com.damoim.restapi.recruit.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
public interface RecruitRepository extends JpaRepository<Recruit, Long> {
}
