package com.shoppingmall.repository;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MemoryProductRepository implements ProductRepository{
    @PersistenceContext
    private final EntityManager em;

    public MemoryProductRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Products save(Products products) { //사용자가 잘 들어갔는지 확인용
        em.persist(products);
        return products;
    }

    @Override
    public List<Products> findByName(String name){
        TypedQuery<Products> query = em.createQuery("SELECT u FROM Products u WHERE u.product_name = :name", Products.class);
        query.setParameter("name", name);
        List<Products> productsList = query.getResultList();
        return productsList;
    }

    @Override
    public List<Products> findByCategory(int category) {
        TypedQuery<Products> query = em.createQuery("SELECT u FROM Products u WHERE u.category = :category", Products.class);
        query.setParameter("category", category);
        List<Products> productsList = query.getResultList();
        return productsList;
    }

    @Override
    public List<Products> findBySellerId(String seller_id) {
        TypedQuery<Products> query = em.createQuery("SELECT u FROM Products u WHERE u.seller_id = :seller_id", Products.class);
        query.setParameter("seller_id", seller_id);
        List<Products> productsList = query.getResultList();
        return productsList;
    }
}
