package com.exercise.tempo.services.impl;

import com.exercise.tempo.dao.MemberRepository;
import com.exercise.tempo.domain.Member;
import com.exercise.tempo.domain.Role;
import com.exercise.tempo.services.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    private final String NULL_VALUE = "null";
    private final String usersEndpoint;
    private final String teamsEndpoint;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    public MemberServiceImpl(@Value("${external-endpoints.users}") String usersEndpoint,
                             @Value("${external-endpoints.teams}") String teamsEndpoint,
                             MemberRepository memberRepository) {
        this.usersEndpoint = usersEndpoint;
        this.teamsEndpoint = teamsEndpoint;
        this.memberRepository = memberRepository;
        restTemplate = new RestTemplate();
    }

    @Override
    public boolean checkForTeamId(UUID teamId) {
        return doesEntityExist(this.teamsEndpoint, teamId);
    }

    @Override
    public boolean checkForUserId(UUID userId) {
        return doesEntityExist(this.usersEndpoint, userId);
    }

    private boolean doesEntityExist(String url, UUID id) {
        ResponseEntity<String> response = restTemplate.getForEntity(url + id.toString(), String.class);
        return !NULL_VALUE.equals(response.getBody());
    }

    @Override
    public List<Member> findMembershipByRoleId(UUID roleId) {
        return memberRepository.findByRoleId(roleId);
    }

    @Override
    public void assignRoleToMember(UUID teamId, UUID userId, Role role) {
        memberRepository.save(new Member(teamId, userId, role));
    }
}
