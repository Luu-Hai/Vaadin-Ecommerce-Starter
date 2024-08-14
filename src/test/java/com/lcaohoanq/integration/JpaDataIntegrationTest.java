package com.lcaohoanq.integration;

import com.lcaohoanq.entity.Role;
import com.lcaohoanq.entity.User;
import com.lcaohoanq.enums.UserRoleEnum;
import com.lcaohoanq.repository.RoleRepository;
import com.lcaohoanq.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "v1API=/api/v1")
@Transactional
public class JpaDataIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testUserInitialization() {
        Optional<User> user = userRepository.findById(1L);
        assertTrue(user.isPresent(), "User with ID 1 should be present");
        assertEquals("hoang", user.get().getFirstName(), "User's first name should be 'hoang'");
    }

    @Test
    public void testRoleInitialization() {
        Optional<Role> role = roleRepository.findById(4);
        assertTrue(role.isPresent(), "Role 'ADMIN' should be present");
        assertEquals(UserRoleEnum.ADMIN, role.get().getRoleName(), "Role name should be 'ADMIN'");
    }

    @Test
    public void testScoreInitialization() {
        Optional<User> user = userRepository.findById(1L);
        assertTrue(user.isPresent(), "Score of User with id 1 should be present");
        assertEquals(90, user.get().getScore().getMax_score(), "User's score should be 0");
    }

    @Test
    public void testSocialAccountInitialization() {
        Optional<User> user = userRepository.findById(1L);
        assertTrue(user.isPresent(), "Status of User with id 1 should be present");
        assertEquals(0, user.get().getSocialAccount().getProvider().getProvider(), "Google provider id should be 0");
    }

    @Test
    public void testUserStatusInitialization() {
        Optional<User> user = userRepository.findById(1L);
        assertTrue(user.isPresent(), "User with ID 1 should be present");
        assertEquals(0, user.get().getStatus().getId(), "User's status should be 'UNVERIFIED'");
    }

}
