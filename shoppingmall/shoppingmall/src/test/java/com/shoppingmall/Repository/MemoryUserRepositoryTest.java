package com.shoppingmall.Repository;

import com.shoppingmall.User.Repository.MemoryUserRepository;
import com.shoppingmall.User.Enum.Level;
import com.shoppingmall.User.Domain.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MemoryUserRepositoryTest {

    @PersistenceContext
    private EntityManager em;
    MemoryUserRepository repository = new MemoryUserRepository(em);

    public MemoryUserRepositoryTest(EntityManager em) {
        this.em = em;
    }

    @AfterEach
    public void afterEach(){
        //repository.clearStore();
    }

    @Test
    public void add() { //사용자가 잘 들어갔는지 확인용
        Users user = new Users();
        user.setId("test1");

        repository.add(user);

        Users res = repository.findById(user.getId());
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
        Users user1 = new Users();
        user1.setId("test1");
        repository.add(user1);

        Users user2 = new Users();
        user2.setId("test2");
        repository.add(user2);

        Users res = repository.findById("test1");
        assertThat(res).isEqualTo(user1);
    }
}
