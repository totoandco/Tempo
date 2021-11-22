package com.exercise.tempo.services;

import com.exercise.tempo.domain.Member;
import com.exercise.tempo.domain.Role;

import java.util.List;
import java.util.UUID;

public interface MemberService {

     boolean checkForTeamId(UUID teamId) ;

     boolean checkForUserId(UUID userId);

    List<Member> findMembershipByRoleId(UUID roleId);

    void assignRoleToMember(UUID teamId, UUID userId, Role role);
}
