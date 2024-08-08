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

    public List<Cart> getCartByUserId(String user_id){
        TypedQuery<Cart> query = em.createQuery("SELECT u FROM Cart u WHERE u.user.id = :user_id", Cart.class);
        query.setParameter("user_id",  user_id);
        return query.getResultList();
    }

    public List<Cart> getCartByProductId(int product_id){
        TypedQuery<Cart> query = em.createQuery("SELECT u FROM Cart u WHERE u.products.id = :product_id", Cart.class);
        query.setParameter("product_id", product_id);
        return query.getResultList();
    }

    public Cart findById(int id){
        return em.find(Cart.class, id);
    }

    public boolean deleteCartAllProduct(int product_id) {
        List<Cart> cart = getCartByProductId(product_id);
        for(int i = 0; i<cart.size(); i++){
            em.remove(cart.get(i));
        }
        return true;
    }

    public boolean deleteCartItem(int id){
        Cart cart = em.find(Cart.class, id);
        if(cart != null) {
            em.remove(cart);
            return true;
        }
        return false;
    }
}
