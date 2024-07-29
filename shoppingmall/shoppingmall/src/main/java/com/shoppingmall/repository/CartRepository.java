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

    public List<Cart> getCart(String user_id){
        TypedQuery<Cart> query = em.createQuery("SELECT u FROM Cart u WHERE u.user.id = :user_id", Cart.class);
        query.setParameter("user_id",  user_id);
        return query.getResultList();
    }

    public Cart findById(int id){
        return em.find(Cart.class, id);
    }
}
