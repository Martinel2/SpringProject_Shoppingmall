package com.shoppingmall.service;

import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.MemoryUserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    UserService userService;
    MemoryUserRepository userRepository;
    private final EntityManager em;
    private PasswordEncoder passwordEncoder;
    public UserServiceTest(EntityManager em, PasswordEncoder passwordEncoder) {
        this.em = em;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository(em);
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach(){
        //userRepository.clearStore();
    }

    @Test
    public void join(){
        Users user = new Users();
        user.setId("test1");

        Users saveUser = (userService.join(user));

        assertEquals(user.getId(), saveUser.getId());
    }

    @Test
    public void validateDupUser(){
        Users user1 = new Users();
        user1.setId("test1");

        Users user2 = new Users();
        user2.setId("test1");

        userService.join(user1);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> userService.join(user2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }
}
