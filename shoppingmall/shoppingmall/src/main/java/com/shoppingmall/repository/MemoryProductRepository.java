package com.shoppingmall.repository;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class MemoryProductRepository implements ProductRepository{
    @PersistenceContext
    private final EntityManager em;

    public MemoryProductRepository(EntityManager em) {
        this.em = em;
    }

    public Products save(Products products) { //사용자가 잘 들어갔는지 확인용
        em.persist(products);
        return products;
    }
}
