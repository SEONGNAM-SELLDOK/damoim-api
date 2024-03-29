package com.damoim.restapi.auth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import lombok.RequiredArgsConstructor;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@EnableWebSecurity(debug = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	protected static final String[] PUBLIC_URIS = {
		"/", "/auth/**", "/h2-db/**", "/v2/api-docs", "/configuration/ui", "/swagger-ui.html", "/webjars/**",
		"/swagger-resources/**", "/configuration/**"
	};

	private final SimpleCorsFilter simpleCorsFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();

		http.httpBasic().disable()
			.formLogin().disable()
			.logout().disable();

		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
			.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
			.antMatchers(PUBLIC_URIS).permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(new JwtAuthenticationFiler(authenticationManagerBean()),
			UsernamePasswordAuthenticationFilter.class);

		http.headers().frameOptions().disable();
	}
}
