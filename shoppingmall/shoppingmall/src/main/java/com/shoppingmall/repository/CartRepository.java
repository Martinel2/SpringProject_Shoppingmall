package com.shoppingmall.repository;

import com.shoppingmall.domain.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CartRepository {
    @PersistenceContext
    private final EntityManager em;

    public CartRepository(EntityManager em) {
        this.em = em;
    }

    public Cart addCart(Cart cart){
        em.persist(cart);
        return cart;
    }

    public List<Cart> getCart(String userId){
        TypedQuery<Cart> query = em.createQuery("SELECT u FROM Cart u WHERE u.userId = :userId", Cart.class);
        query.setParameter("userId",  userId);
        return query.getResultList();
    }
}
