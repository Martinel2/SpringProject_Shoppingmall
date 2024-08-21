package com.shoppingmall.repository;

import com.shoppingmall.domain.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ReviewRepository {

    @PersistenceContext
    private final EntityManager em;

    public ReviewRepository(EntityManager em) {
        this.em = em;
    }

    public Review writeReview(Review review){
        em.persist(review);
        return review;
    }

    public List<Review> getReviewByUserId(String user_id){
        TypedQuery<Review> query = em.createQuery("SELECT u FROM Review u WHERE u.users.id = :user_id", Review.class);
        query.setParameter("user_id",  user_id);
        return query.getResultList();
    }

}
