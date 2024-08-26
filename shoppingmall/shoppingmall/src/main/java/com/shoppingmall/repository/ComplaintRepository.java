package com.shoppingmall.repository;

import com.shoppingmall.domain.Complaint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ComplaintRepository {

    @PersistenceContext
    private final EntityManager em;


    public ComplaintRepository(EntityManager em) {
        this.em = em;
    }


    public Complaint addComplain(Complaint complaint){
        em.persist(complaint);
        return complaint;
    }
}
