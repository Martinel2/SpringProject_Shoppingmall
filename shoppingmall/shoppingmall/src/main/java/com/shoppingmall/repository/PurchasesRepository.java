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

    public Purchases findById(int id){
        return em.find(Purchases.class, id);
    }

    public List<Purchases> getPurchasesByUserId(String user_id){
        TypedQuery<Purchases> query = em.createQuery("SELECT u FROM Purchases u WHERE u.user_id = :user_id", Purchases.class);
        query.setParameter("user_id",  user_id);
        return query.getResultList();
    }

    public List<Purchases> getPurchaseTerm(String user_id, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        String jpql = "SELECT p FROM Purchases p WHERE p.users.id = :user_id AND p.created BETWEEN :startDateTime AND :endDateTime";
        TypedQuery<Purchases> query = em.createQuery(jpql, Purchases.class);
        query.setParameter("user_id", user_id);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);

        return query.getResultList();
    }

    public List<Purchases> getPurchaseTermPlusKeyword(String user_id, LocalDateTime startDateTime, LocalDateTime endDateTime, String keyword) {

        String jpql = "SELECT p FROM Purchases p WHERE p.users.id = :user_id AND p.products.product_name like :keyword AND p.created BETWEEN :startDateTime AND :endDateTime";
        TypedQuery<Purchases> query = em.createQuery(jpql, Purchases.class);
        query.setParameter("user_id", user_id);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }


    public List<Purchases> findByUserIdAndOrderId(String user_id, String order_id){
        String jpql = "SELECT p FROM Purchases p WHERE p.users.id = :user_id AND p.order_id = :order_id";
        TypedQuery<Purchases> query = em.createQuery(jpql, Purchases.class);
        query.setParameter("user_id", user_id);
        query.setParameter("order_id", order_id);

        return query.getResultList();
    }

    public List<Purchases> findByProductIdAndOrderId(int product_id, String order_id){
        String jpql = "SELECT p FROM Purchases p WHERE p.products.id = :product_id AND p.order_id = :order_id";
        TypedQuery<Purchases> query = em.createQuery(jpql, Purchases.class);
        query.setParameter("product_id", product_id);
        query.setParameter("order_id", order_id);

        return query.getResultList();
    }

    public Purchases findByThreeId(String user_id, int product_id, String order_id){
        String jpql = "SELECT p FROM Purchases p WHERE p.products.id = :product_id AND p.order_id = :order_id AND p.users.id = :user_id";
        TypedQuery<Purchases> query = em.createQuery(jpql, Purchases.class);
        query.setParameter("product_id", product_id);
        query.setParameter("user_id", user_id);
        query.setParameter("order_id", order_id);

        return query.getSingleResult();
    }

    public boolean deletePurchases(Purchases purchases) {
        if(purchases != null) {
            purchases = em.merge(purchases);
            em.remove(purchases);
            return true;
        }
        else return false;
    }
}
