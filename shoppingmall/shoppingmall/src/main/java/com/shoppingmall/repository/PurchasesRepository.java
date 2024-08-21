package com.shoppingmall.repository;

import com.shoppingmall.domain.Purchases;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
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

    public List<Purchases> getPurchaseWithinOneMonth(String user_id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthBef = now.minusMonths(1);

        String jpql = "SELECT p FROM Purchases p WHERE p.users.id = :user_id AND p.created BETWEEN :oneMonthBef AND :now";
        TypedQuery<Purchases> query = em.createQuery(jpql, Purchases.class);
        query.setParameter("user_id", user_id);
        query.setParameter("now", now);
        query.setParameter("oneMonthBef", oneMonthBef);

        return query.getResultList();
    }

}
