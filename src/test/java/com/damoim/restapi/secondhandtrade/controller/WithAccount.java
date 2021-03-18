package com.damoim.restapi.secondhandtrade.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * @author dkansk924@naver.com
 * @since 2021. 03. 17
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAccountSecurityContextFactory.class)
public @interface WithAccount {

    String value();
}
