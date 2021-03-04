package com.damoim.restapi.member.dao;

import com.damoim.restapi.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

/**
 * MemberRepository
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
public interface MemberRepository extends CrudRepository<Member, Long> {

}
