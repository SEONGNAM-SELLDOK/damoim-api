package com.damoim.restapi.secondhandtrade.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  // 시큐리티 의존성 추가로인한 401 error 를 처리하기위한 임시 config 입니다.
  // "/login" 요청을 제외한 모든 요청에 대해서 인증을 받지않음.
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/login").authenticated()
        .anyRequest().permitAll();

    http
        .headers()
        .frameOptions()
        .disable();
  }
}
