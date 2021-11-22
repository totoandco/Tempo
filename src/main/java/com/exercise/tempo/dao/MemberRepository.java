package com.exercise.tempo.dao;

import com.exercise.tempo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    @Query("SELECT m " +
            "FROM Member m " +
            "WHERE m.role.id = :RoleId ")
    List<Member> findByRoleId(@Param("RoleId") UUID roleId);
}
