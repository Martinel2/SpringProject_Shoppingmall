package com.shoppingmall.User.Repository;

import com.shoppingmall.User.Domain.Seller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class SellerRepository {

    @PersistenceContext
    private final EntityManager em;


    public SellerRepository(EntityManager em) {
        this.em = em;
    }
    public Seller findById(String id) {
        return em.find(Seller.class, id);
    }

    public Seller add(Seller seller) { //사용자가 잘 들어갔는지 확인
        em.persist(seller);
        return seller;
    }

}
