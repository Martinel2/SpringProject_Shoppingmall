package com.shoppingmall.repository;

import com.shoppingmall.domain.Purchases;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PurchasesRepository {
    @PersistenceContext
    private final EntityManager em;

    public PurchasesRepository(EntityManager em) {
        this.em = em;
    }

    public Purchases addPurchases(Purchases purchases){
        em.persist(purchases);
        return purchases;
    }

    public List<Purchases> getPurchasesByUserId(String user_id){
        TypedQuery<Purchases> query = em.createQuery("SELECT u FROM Purchases u WHERE u.user_id = :user_id", Purchases.class);
        query.setParameter("user_id",  user_id);
        return query.getResultList();
    }
}
