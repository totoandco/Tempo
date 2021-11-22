package com.exercise.tempo.domain.mapper;

import com.exercise.tempo.domain.Role;
import com.exercise.tempo.domain.dto.CreateRoleDTO;
import com.exercise.tempo.domain.dto.RoleDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-21T21:16:33-0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.6 (Oracle Corporation)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDTO toRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setName( role.getName() );
        roleDTO.setId( role.getId() );

        return roleDTO;
    }

    @Override
    public Role toRole(CreateRoleDTO createRoleDTO) {
        if ( createRoleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setName( createRoleDTO.getName() );

        return role;
    }
}
