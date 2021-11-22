package com.exercise.tempo.dao;

import com.exercise.tempo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Query("SELECT r " +
            "FROM Member m " +
            "join m.role r " +
            "WHERE m.userId = :UserId and m.teamId = :TeamId")
    Optional<Role> findRoleForMembership(@Param("UserId") UUID userId, @Param("TeamId") UUID teamId);
}
