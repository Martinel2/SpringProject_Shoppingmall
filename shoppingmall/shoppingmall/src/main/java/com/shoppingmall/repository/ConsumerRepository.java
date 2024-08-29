package com.shoppingmall.repository;

import com.shoppingmall.domain.Consumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ConsumerRepository {
    @PersistenceContext
    private final EntityManager em;

    public ConsumerRepository(EntityManager em) {
        this.em = em;
    }

    public Consumer save(Consumer consumer){
        em.persist(consumer);
        return consumer;
    }
}
