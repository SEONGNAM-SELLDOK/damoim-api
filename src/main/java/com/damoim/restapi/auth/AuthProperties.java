package com.damoim.restapi.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**  * FileProperties
 *
 * @author incheol.jung
 * @since 2021. 01. 10.
 */
@Configuration
@ConfigurationProperties(prefix = "damoim.auth")
@Getter
@Setter
public class AuthProperties {
	private String clientId;
	private String secret;
	private String signSecret;
}
