package com.exercise.tempo.services;

import com.exercise.tempo.dao.RoleRepository;
import com.exercise.tempo.domain.Role;
import com.exercise.tempo.services.impl.RoleServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RoleServiceImpl.class})
public class RoleServiceTest {


    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;


    @Test
    public void findRoleForMembershipResultFoundTest() {
        // Given
        Optional<Role> expectedResult = Optional.of(new Role());
        Mockito.when(roleRepository.findRoleForMembership(any(), any())).thenReturn(expectedResult);

        // When
        Optional<Role> result = roleService.findRoleForMembership(UUID.randomUUID(), UUID.randomUUID());

        // Then
        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void findRoleForMembershipResultNotFoundTest() {
        // Given
        Optional<Role> expectedResult = Optional.empty();
        Mockito.when(roleRepository.findRoleForMembership(any(), any())).thenReturn(expectedResult);

        // When
        Optional<Role> result = roleService.findRoleForMembership(UUID.randomUUID(), UUID.randomUUID());

        // Then
        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void findRoleResultNotFoundTest() {
        // Given
        Optional<Role> expectedResult = Optional.empty();
        Mockito.when(roleRepository.findById(any())).thenReturn(expectedResult);

        // When
        Optional<Role> result = roleService.findRole(UUID.randomUUID());

        // Then
        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void findRoleResultFoundTest() {
        // Given
        Optional<Role> expectedResult = Optional.of(new Role());
        Mockito.when(roleRepository.findById(any())).thenReturn(expectedResult);

        // When
        Optional<Role> result = roleService.findRole(UUID.randomUUID());

        // Then
        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void findRolesOkTest() {
        // Given
        List<Role> expectedResult = new ArrayList<>();
        Mockito.when(roleRepository.findAll()).thenReturn(expectedResult);

        // When
        List<Role> result = roleService.findRoles();

        // Then
        Assert.assertEquals(result, expectedResult);
    }

}
