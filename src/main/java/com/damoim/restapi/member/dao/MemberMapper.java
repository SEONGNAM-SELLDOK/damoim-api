package com.damoim.restapi.member.dao;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.SaveMemberResponse;
import org.mapstruct.Mapper;

/**
 * MemberMapper
 *
 * @author incheol.jung
 * @since 2021. 02. 20.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<SaveMemberResponse, Member> {

}
