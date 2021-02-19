package com.damoim.restapi.employee.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**  * SaveMemberRequest
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
@Builder
@Getter
@Setter
public class SaveMemberRequest {

	private String id;
	private String name;
	private String pwd;
	private String profilePicUrl;
	private String register;
}
