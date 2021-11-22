package com.exercise.tempo.services;

import com.exercise.tempo.dao.MemberRepository;
import com.exercise.tempo.domain.Member;
import com.exercise.tempo.services.impl.MemberServiceImpl;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MemberServiceImpl.class})
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    public void findMembershipByRoleIdResultFoundTest() {
        // Given
        List<Member> expectedResult = new ArrayList<>();
        Mockito.when(memberRepository.findByRoleId(any())).thenReturn(expectedResult);

        // When
        List<Member> result = memberService.findMembershipByRoleId(UUID.randomUUID());

        // Then
        Assert.assertEquals(result, expectedResult);
    }

    /**
     * Test depends of the current content of the provided API, can fail if user doesn't exist in the system
     * It should be transformed into an integration test where we are mocking the users endpoint
     */
    @Test
    public void checkForUserIdExistingUser() {
        // Given
        UUID existingUserId = UUID.fromString("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c");

        // When
        boolean result = memberService.checkForUserId(existingUserId);

        // Then
        Assert.assertTrue(result);
    }

    /**
     * Test depends of the current content of the provided API, can fail if user exists in the system
     * It should be transformed into an integration test where we are mocking the users endpoint
     */
    @Test
    public void checkForUserIdNonExistingUser() {
        // Given
        UUID nonExistingUserId = UUID.fromString("62b302ea-ef94-407f-aa52-101bd0af92cc");

        // When
        boolean result = memberService.checkForUserId(nonExistingUserId);

        // Then
        Assert.assertFalse(result);
    }

    /**
     * Test depends of the current content of the provided API, can fail if team doesn't exist in the system
     * It should be transformed into an integration test where we are mocking the teams endpoint
     */
    @Test
    public void checkForTeamIdExistingTeam() {
        // Given
        UUID existingTeamId = UUID.fromString("7676a4bf-adfe-415c-941b-1739af07039b");

        // When
        boolean result = memberService.checkForTeamId(existingTeamId);

        // Then
        Assert.assertTrue(result);
    }

    /**
     * Test depends of the current content of the provided API, can fail if team exists in the system
     * It should be transformed into an integration test where we are mocking the teams endpoint
     */
    @Test
    public void checkForTeamIdNonExistingTeam() {
        // Given
        UUID nonExistingTeamId = UUID.fromString("62b302ea-ef94-407f-aa52-101bd0af92cc");

        // When
        boolean result = memberService.checkForTeamId(nonExistingTeamId);

        // Then
        Assert.assertFalse(result);
    }

}
