package com.damoim.restapi.member.dao;

import com.damoim.restapi.member.entity.Member;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;

/**
 * MemberRepository
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
public interface MemberRepository extends CrudRepository<Member, Long> {

    Member findByEmail(String email);
}
