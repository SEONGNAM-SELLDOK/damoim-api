package com.damoim.restapi.employee.model;

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(value = "회원 아이디(이메일)", required = true, example = "incheol@naver.com")
	private String id;
	@ApiModelProperty(value = "회원 이름", required = true, example = "정인철")
	private String name;
	@ApiModelProperty(value = "비밀번호", required = true, example = "password")
	private String pwd;
	@ApiModelProperty(value = "프로필 사진 경로", example = "/img/profile/profile.png")
	private String profilePicUrl;
	@ApiModelProperty(value = "등록자", required = true, example = "정인철")
	private String register;
}
