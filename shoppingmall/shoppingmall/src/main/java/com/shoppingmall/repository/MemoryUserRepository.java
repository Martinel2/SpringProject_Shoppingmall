package com.shoppingmall.repository;

import com.shoppingmall.domain.Level;
import com.shoppingmall.domain.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class MemoryUserRepository implements UserRepository {
    @PersistenceContext
    private final EntityManager em;

    public MemoryUserRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Users add(Users user) { //사용자가 잘 들어갔는지 확인용
        em.persist(user);
        return user;
    }

    @Override
    public Level valueOf(int value) {
        return switch (value) {
            case 1 -> Level.BASIC;
            case 2 -> Level.NORMAL;
            case 3 -> Level.VIP;
            case 4 -> Level.VVIP;
            default -> throw new AssertionError("Unknown value: " + value);
        };
    }

    @Override
    public Users findById(String id) {
        return em.find(Users.class, id);
    }


}
