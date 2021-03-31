package com.damoim.restapi.introduce.dao;

import com.damoim.restapi.introduce.entity.Introduce;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SeongRok.Oh
 * @since 2021/03/31
 */
public interface IntroRepository extends JpaRepository<Introduce, Long> {
}
