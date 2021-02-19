package com.damoim.restapi.member.dao;

import org.mapstruct.Mapper;

import com.damoim.restapi.config.jpa.EntityMapper;
import com.damoim.restapi.member.entity.Member;
import com.damoim.restapi.member.model.SaveMemberResponse;

/**  * MemberMapper
 *
 * @author incheol.jung
 * @since 2021. 02. 20.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<SaveMemberResponse, Member> {

}
