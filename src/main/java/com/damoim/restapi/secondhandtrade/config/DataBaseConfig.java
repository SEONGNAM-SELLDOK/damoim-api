package com.damoim.restapi.secondhandtrade.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseConfig {

  @Bean
  public JPAQueryFactory jpaQueryFactory(EntityManager em){
    return new JPAQueryFactory(em);
  }

}
