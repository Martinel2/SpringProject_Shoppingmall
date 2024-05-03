package com.shoppingmall.repository;

import com.shoppingmall.domain.Level;
import com.shoppingmall.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;

public class MemoryUserRepository implements UserRepository {
    @PersistenceContext
    private final EntityManager em;

    public MemoryUserRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public User add(User user) { //사용자가 잘 들어갔는지 확인용
        em.persist(user);
        return user;
    }

    @Override
    public Level valueOf(int value) {
        switch (value){
            case 1: return Level.BASIC;
            case 2: return Level.NORMAL;
            case 3: return Level.VIP;
            case 4: return Level.VVIP;
            default: throw new AssertionError("Unknown value: " + value);
        }
    }

    @Override
    public User findById(String id) {
        User user = em.find(User.class, id);
        return user;
    }


}
