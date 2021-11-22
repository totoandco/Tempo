package com.exercise.tempo.domain.mapper;

import com.exercise.tempo.domain.Member;
import com.exercise.tempo.domain.dto.MemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "teamId", source = "teamId")
    MemberDTO toMemberDTO(Member member);
}
