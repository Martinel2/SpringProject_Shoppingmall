package com.shoppingmall.service;

import com.shoppingmall.domain.User;
import com.shoppingmall.repository.MemoryUserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    UserService userService;
    MemoryUserRepository userRepository;
    private final EntityManager em;

    public UserServiceTest(EntityManager em) {
        this.em = em;
    }

    @BeforeEach
    public void beforeEach() {
        userRepository = new MemoryUserRepository(em);
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void afterEach(){
        userRepository.clearStore();
    }

    @Test
    public void join(){
        User user = new User();
        user.setId("test1");

        String saveId = userService.join(user);

        User isUser = userRepository.findById(saveId).get();
        assertEquals(user.getId(), isUser.getId());
    }

    @Test
    public void validateDupUser(){
        User user1 = new User();
        user1.setId("test1");

        User user2 = new User();
        user2.setId("test1");

        userService.join(user1);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> userService.join(user2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }
}
