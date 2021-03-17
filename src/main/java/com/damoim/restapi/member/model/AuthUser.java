package com.damoim.restapi.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**  * TestUser
 *
 * @author incheol.jung
 * @since 2021. 03. 10.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthUser {
	private String name;
	private String email;
}
