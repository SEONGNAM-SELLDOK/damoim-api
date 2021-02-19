package com.damoim.restapi.member.dao;

import org.springframework.data.repository.CrudRepository;

import com.damoim.restapi.member.entity.Member;

/**  * MemberRepository
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
}
