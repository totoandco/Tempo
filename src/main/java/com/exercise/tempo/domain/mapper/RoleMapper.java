package com.exercise.tempo.domain.mapper;

import com.exercise.tempo.domain.Role;
import com.exercise.tempo.domain.dto.CreateRoleDTO;
import com.exercise.tempo.domain.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "id", source = "id")
    RoleDTO toRoleDTO(Role role);

    @Mapping(target = "name", source = "name")
    Role toRole(CreateRoleDTO createRoleDTO);
}
