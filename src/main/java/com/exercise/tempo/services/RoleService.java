package com.exercise.tempo.services;

import com.exercise.tempo.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {

    Role saveRole(Role role);

    Optional<Role> findRole(UUID roleId);

    Optional<Role> findRoleForMembership(UUID userId, UUID teamId);

    List<Role> findRoles();
}
