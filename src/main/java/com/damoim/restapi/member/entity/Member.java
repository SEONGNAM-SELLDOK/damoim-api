package com.damoim.restapi.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**  * Member
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String id;
	private String name;
	private String pwd;
	private String profilePicUrl;
	@Column(updatable = false)
	private String register;
	@CreatedDate
	private LocalDateTime registeredDate;
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	private String modifier;
}
