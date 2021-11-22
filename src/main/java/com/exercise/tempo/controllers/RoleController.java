package com.exercise.tempo.controllers;

import com.exercise.tempo.domain.Role;
import com.exercise.tempo.domain.dto.CreateRoleDTO;
import com.exercise.tempo.domain.dto.MemberDTO;
import com.exercise.tempo.domain.dto.RoleDTO;
import com.exercise.tempo.domain.mapper.MemberMapper;
import com.exercise.tempo.domain.mapper.RoleMapper;
import com.exercise.tempo.services.MemberService;
import com.exercise.tempo.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Roles", description = "Roles resource.")
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;
    private final MemberService memberService;

    public RoleController(RoleService roleService, MemberService memberService) {
        this.roleService = roleService;
        this.memberService = memberService;
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find roles", tags = {"Roles"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoleDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity getRoles() {
        List<RoleDTO> roles = roleService.findRoles().stream()
                .map(role-> RoleMapper.INSTANCE.toRoleDTO(role))
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }

    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find role by id", tags = {"Roles"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role for the requested id.", content = @Content(schema = @Schema(implementation = RoleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Role for requested id not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity getRoleForMembership(
            @Parameter(description = "Role id", required = true)
            @PathVariable(value = "roleId") UUID roleId) {

        Optional<Role> optionalRole = roleService.findRole(roleId);
        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RoleMapper.INSTANCE.toRoleDTO(optionalRole.get()));
    }

    @RequestMapping(value = "/teams/{teamId}/users/{userId}/role", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find role for membership", tags = {"Roles"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role for the requested membership.", content = @Content(schema = @Schema(implementation = RoleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Role for membership not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity getRoleForMembership(
            @Parameter(description = "Team id", required = true)
            @PathVariable(value = "teamId") UUID teamId,
            @Parameter(description = "User id", required = true)
            @PathVariable(value = "userId") UUID userId) {

        Optional<Role> optionalRole = roleService.findRoleForMembership(userId, teamId);

        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RoleMapper.INSTANCE.toRoleDTO(optionalRole.get()));
    }

    @RequestMapping(value = "/roles/{roleId}/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find members for role", tags = {"Roles"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membership for role.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MemberDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Role not fount."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity getMembershipForRole(
            @Parameter(description = "Role id", required = true)
            @PathVariable(value = "roleId") UUID roleId) {

        Optional<Role> optionalRole = roleService.findRole(roleId);
        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<MemberDTO> members = memberService.findMembershipByRoleId(roleId).stream()
                .map(member -> MemberMapper.INSTANCE.toMemberDTO(member))
                .collect(Collectors.toList());

        return ResponseEntity.ok(members);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new role in the system", tags = {"Roles"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created.", content = @Content(schema = @Schema(implementation = RoleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Missing parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity createNewRole(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Role to create", content = @Content(schema = @Schema(implementation = CreateRoleDTO.class)))
            @RequestBody CreateRoleDTO createRoleDTO) {

        if (createRoleDTO.getName() == null) {
            return ResponseEntity.badRequest().build();
        }

        Role role = roleService.saveRole(RoleMapper.INSTANCE.toRole(createRoleDTO));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();
        return ResponseEntity.created(location).body(RoleMapper.INSTANCE.toRoleDTO(role));
    }

    @RequestMapping(value = "/teams/{teamId}/users/{userId}/role/{roleId}", method = RequestMethod.PUT)
    @Operation(summary = "Assign a role to a member.", tags = {"Roles"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role assigned to the member."),
            @ApiResponse(responseCode = "404", description = "Role, User or Team cannot be found in the system."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity assignRoleToMember(
            @Parameter(description = "Team id", required = true)
            @PathVariable(value = "teamId") UUID teamId,
            @Parameter(description = "User id", required = true)
            @PathVariable(value = "userId") UUID userId,
            @Parameter(name = "roleId", description = "Id of the role", required = true, schema = @Schema(implementation = UUID.class))
            @PathVariable("roleId") UUID roleId) {

        Optional<Role> optionalRole = roleService.findRole(roleId);

        if (optionalRole.isEmpty() || !memberService.checkForTeamId(teamId) || !memberService.checkForUserId(userId)) {
            return ResponseEntity.notFound().build();
        }

        memberService.assignRoleToMember(teamId, userId, optionalRole.get());

        return ResponseEntity.noContent().build();
    }

}

