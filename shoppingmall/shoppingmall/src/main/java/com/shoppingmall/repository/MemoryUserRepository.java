package com.shoppingmall.repository;

import com.shoppingmall.domain.Users;
import com.shoppingmall.dto.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class MemoryUserRepository implements UserRepository {
    @PersistenceContext
    private final EntityManager em;

    public MemoryUserRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Users add(Users user) { //사용자가 잘 들어갔는지 확인
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


    @Override
    public Users findByEmail(String email){
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.email = :email", Users.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public boolean deleteUser(Users users) {
        if(users != null) {
            users = em.merge(users);
            em.remove(users);
            return true;
        }
        else return false;
    }
}
