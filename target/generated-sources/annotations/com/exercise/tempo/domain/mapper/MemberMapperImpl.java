package com.exercise.tempo.domain.mapper;

import com.exercise.tempo.domain.Member;
import com.exercise.tempo.domain.dto.MemberDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-21T21:16:33-0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.6 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDTO toMemberDTO(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setUserId( member.getUserId() );
        memberDTO.setTeamId( member.getTeamId() );

        return memberDTO;
    }
}
