package com.damoim.restapi.member.dao;

import org.springframework.data.repository.CrudRepository;

import com.damoim.restapi.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MemberRepository
 *
 * @author incheol.jung
 * @since 2021. 02. 19.
 */
@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByNo(long no);
}
