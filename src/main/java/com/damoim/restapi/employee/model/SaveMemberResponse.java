package com.damoim.restapi.employee.model;

import java.time.LocalDateTime;

import javax.persistence.Column;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

/**  * SaveMemberResponse
 *
 * @author incheol.jung
 * @since 2021. 02. 20.
 */
@Getter
@Setter
public class SaveMemberResponse {
	private long no;
	private String id;
	private String name;
	private String pwd;
	private String profilePicUrl;
	private String register;
	private LocalDateTime registeredDate;
	private String modifier;
	private LocalDateTime modifiedDate;
}
