package com.shoppingmall.repository;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.domain.Wishlist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class WishlistRepository {

    @PersistenceContext
    public EntityManager em;

    public Wishlist save(Users user, Products products) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(products);
        wishlist.setUser_id(user.getId());
        wishlist.setProduct_id(products.getId());
        em.persist(wishlist);
        return wishlist;
    }

    public List<Wishlist> getWishlistByUserId(String id){
        TypedQuery<Wishlist> query = em.createQuery("SELECT u FROM Wishlist u WHERE u.user_id = :id", Wishlist.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Wishlist> getWishlistByProductId(int id){
        TypedQuery<Wishlist> query = em.createQuery("SELECT u FROM Wishlist u WHERE u.product_id = :id", Wishlist.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public boolean deleteWishlist(int id){
        Wishlist wishlist = em.find(Wishlist.class, id);
        if(wishlist != null) {
            em.remove(wishlist);
            return true;
        }
        return false;
    }
}
