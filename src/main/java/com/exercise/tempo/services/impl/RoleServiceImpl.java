package com.exercise.tempo.services.impl;


import com.exercise.tempo.dao.RoleRepository;
import com.exercise.tempo.domain.Role;
import com.exercise.tempo.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findRole(UUID roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public Optional<Role> findRoleForMembership(UUID userId, UUID teamId) {
        return roleRepository.findRoleForMembership(userId, teamId);
    }

    @Override
    public List<Role> findRoles() {
        return roleRepository.findAll();
    }

}
