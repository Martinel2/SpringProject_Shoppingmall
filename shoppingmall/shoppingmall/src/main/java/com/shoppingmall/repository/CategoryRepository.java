package com.shoppingmall.repository;

import com.shoppingmall.domain.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    @PersistenceContext
    private final EntityManager em;

    public CategoryRepository(EntityManager em) {
        this.em = em;
    }

    public List<Integer> findIdByTitle(String title){
        TypedQuery<Category> query = em.createQuery("SELECT u FROM Category u WHERE u.title LIKE :title", Category.class);
        query.setParameter("title", "%" + title + "%");
        List<Category> category = query.getResultList();
        List<Integer> id = new ArrayList<>();
        for (Category value : category) {
            id.add(value.getId());
        }
        return id;
    }

    public List<Category> findAll() {
        String jpql = "SELECT c FROM Category c";
        TypedQuery<Category> query = em.createQuery(jpql, Category.class);
        return query.getResultList();
    }
}
