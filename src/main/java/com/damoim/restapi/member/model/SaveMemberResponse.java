package com.damoim.restapi.member.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * SaveMemberResponse
 *
 * @author incheol.jung
 * @since 2021. 02. 20.
 */
@Getter
@Setter
public class SaveMemberResponse {

    @ApiModelProperty(example = "1")
    private long no;
    @ApiModelProperty(value = "회원 아이디(이메일)", example = "incheol@naver.com")
    private String id;
    @ApiModelProperty(value = "회원 이름", example = "정인철")
    private String name;
    @ApiModelProperty(value = "비밀번호", example = "password")
    private String pwd;
    @ApiModelProperty(value = "프로필 사진 경로", example = "http://localhost:8080/profiles/me.png")
    private String profilePicUrl;
    @ApiModelProperty(value = "등록자", example = "정인철")
    private String register;
    @ApiModelProperty(value = "등록일", example = "2021-02-19T15:43:22.137Z")
    private LocalDateTime registeredDate;
    @ApiModelProperty(value = "수정자", example = "정인철")
    private String modifier;
    @ApiModelProperty(value = "수정일", example = "2021-02-19T15:43:22.137Z")
    private LocalDateTime modifiedDate;
}
