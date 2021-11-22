package com.exercise.tempo.controllers;

import com.exercise.tempo.domain.Member;
import com.exercise.tempo.domain.Role;
import com.exercise.tempo.domain.dto.CreateRoleDTO;
import com.exercise.tempo.services.MemberService;
import com.exercise.tempo.services.RoleService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {RoleController.class})
public class RoleControllerTest {

    private static final String ASSIGN_ROLE_TO_MEMBER_URL = "/api/teams/%s/users/%s/role/%s";
    private static final String ROLE_URL = "/api/roles";
    private static final String GET_ROLE_URL = "/api/roles/%s";
    private static final String GET_ROLE_FOR_MEMBER_URL = "/api/teams/%s/users/%s/role";
    private static final String GET_MEMBER_FOR_ROLE_URL = "/api/roles/%s/members";

    private final Gson gson = new Gson();

    @MockBean
    private RoleService roleService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void assignRoleToMemberRoleNotFoundTest() throws Exception {
        // Given
        UUID roleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        Mockito.when(roleService.findRole(roleId)).thenReturn(Optional.empty());
        Mockito.when(memberService.checkForUserId(userId)).thenReturn(true);
        Mockito.when(memberService.checkForTeamId(teamId)).thenReturn(true);
        String url = String.format(ASSIGN_ROLE_TO_MEMBER_URL, teamId, userId, roleId);

        // Then
        mvc.perform(put(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void assignRoleToMemberUserNotFoundTest() throws Exception {
        // Given
        UUID roleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        Mockito.when(roleService.findRole(roleId)).thenReturn(Optional.of(new Role()));
        Mockito.when(memberService.checkForUserId(userId)).thenReturn(false);
        Mockito.when(memberService.checkForTeamId(teamId)).thenReturn(true);
        String url = String.format(ASSIGN_ROLE_TO_MEMBER_URL, teamId, userId, roleId);

        // Then
        mvc.perform(put(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void assignRoleToMemberTeamNotFoundTest() throws Exception {
        // Given
        UUID roleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        Mockito.when(roleService.findRole(roleId)).thenReturn(Optional.of(new Role()));
        Mockito.when(memberService.checkForUserId(userId)).thenReturn(true);
        Mockito.when(memberService.checkForTeamId(teamId)).thenReturn(false);
        String url = String.format(ASSIGN_ROLE_TO_MEMBER_URL, teamId, userId, roleId);

        // Then
        mvc.perform(put(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void assignRoleToMemberOkNoContentTest() throws Exception {
        // Given
        UUID roleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        Mockito.when(roleService.findRole(roleId)).thenReturn(Optional.of(new Role()));
        Mockito.when(memberService.checkForUserId(userId)).thenReturn(true);
        Mockito.when(memberService.checkForTeamId(teamId)).thenReturn(true);
        String url = String.format(ASSIGN_ROLE_TO_MEMBER_URL, teamId, userId, roleId);

        // Then
        mvc.perform(put(url))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createRoleWithoutNameTest() throws Exception {
        // Given
        Role role = new Role();
        CreateRoleDTO createRoleDTO = new CreateRoleDTO();
        Mockito.when(roleService.saveRole(any())).thenReturn(role);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .post(ROLE_URL)
                .content(gson.toJson(createRoleDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createRoleOkTest() throws Exception {
        // Given
        Role role = new Role();
        CreateRoleDTO createRoleDTO = new CreateRoleDTO();
        createRoleDTO.setName("test");
        Mockito.when(roleService.saveRole(any())).thenReturn(role);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .post(ROLE_URL)
                .content(gson.toJson(createRoleDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getRoleRoleNotFoundTest() throws Exception {
        // Given
        UUID roleId = UUID.randomUUID();
        Mockito.when(roleService.findRole(roleId)).thenReturn(Optional.empty());
        String url = String.format(GET_ROLE_URL, roleId);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRoleOkTest() throws Exception {
        // Given
        UUID roleId = UUID.randomUUID();
        Mockito.when(roleService.findRole(roleId)).thenReturn(Optional.of(new Role()));
        String url = String.format(GET_ROLE_URL, roleId);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getRoleForMembershipRoleNotFoundTest() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        Mockito.when(roleService.findRoleForMembership(userId, teamId)).thenReturn(Optional.empty());
        String url = String.format(GET_ROLE_FOR_MEMBER_URL, teamId, userId);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRoleForMembershipOkTest() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        Mockito.when(roleService.findRoleForMembership(userId, teamId)).thenReturn(Optional.of(new Role()));
        String url = String.format(GET_ROLE_FOR_MEMBER_URL, teamId, userId);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getMembershipForRoleRoleNotFoundTest() throws Exception {
        // Given
        UUID roleId = UUID.randomUUID();
        List<Member> members = new ArrayList<>();
        Mockito.when(roleService.findRole(roleId)).thenReturn(Optional.empty());
        Mockito.when(memberService.findMembershipByRoleId(roleId)).thenReturn(members);
        String url = String.format(GET_MEMBER_FOR_ROLE_URL, roleId);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMembershipForRoleOkTest() throws Exception {
        // Given
        UUID roleId = UUID.randomUUID();
        List<Member> members = new ArrayList<>();
        Mockito.when(roleService.findRole(roleId)).thenReturn(Optional.of(new Role()));
        Mockito.when(memberService.findMembershipByRoleId(roleId)).thenReturn(members);
        String url = String.format(GET_MEMBER_FOR_ROLE_URL, roleId);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getRolesTest() throws Exception {
        // Given
        List<Role> roles = new ArrayList<>();
        Mockito.when(roleService.findRoles()).thenReturn(roles);

        // Then
        mvc.perform(MockMvcRequestBuilders
                .get(ROLE_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
