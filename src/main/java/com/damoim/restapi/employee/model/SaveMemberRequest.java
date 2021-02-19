package com.damoim.restapi.employee.model;

import io.swagger.annotations.ApiParam;
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
	@ApiParam(value = "회원 아이디(이메일)", required = true, example = "incheol@naver.com")
	private String id;
	@ApiParam(value = "회원 이름", required = true, example = "정인철")
	private String name;
	@ApiParam(value = "비밀번호", required = true, example = "password")
	private String pwd;
	@ApiParam(value = "프로필 사진 경로", example = "/img/profile/profile.png")
	private String profilePicUrl;
	@ApiParam(value = "등록자", required = true, example = "정인철")
	private String register;
}
