package com.damoim.restapi.employee.dao;

import org.springframework.data.repository.CrudRepository;

import com.damoim.restapi.employee.entity.Member;

/**  * MemberRepository
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
}
