package com.shoppingmall.repository;

import com.shoppingmall.domain.Level;
import com.shoppingmall.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MemoryUserRepositoryTest {
    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void add() { //사용자가 잘 들어갔는지 확인용
        User user = new User();
        user.setId("test1");

        repository.add(user);

        User res = repository.findById(user.getId()).get();
        assertThat(res).isEqualTo(user);
    }

    @Test
    public void valueOf(){
        assertThat(repository.valueOf(1)).isEqualTo(Level.BASIC);
        assertThat(repository.valueOf(2)).isEqualTo(Level.NORMAL);
        assertThat(repository.valueOf(3)).isEqualTo(Level.VIP);
        assertThat(repository.valueOf(4)).isEqualTo(Level.VVIP);
        Assertions.assertThrows(AssertionError.class, () ->{
            repository.valueOf(5);
        });
    }

    @Test
    public void findById() {
        User user1 = new User();
        user1.setId("test1");
        repository.add(user1);

        User user2 = new User();
        user2.setId("test2");
        repository.add(user2);

        User res = repository.findById("test1").get();
        assertThat(res).isEqualTo(user1);
    }
}
