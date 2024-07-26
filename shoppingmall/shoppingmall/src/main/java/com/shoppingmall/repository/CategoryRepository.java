package com.shoppingmall.repository;

import com.shoppingmall.domain.Category;
import com.shoppingmall.domain.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CategoryRepository {

    @PersistenceContext
    private final EntityManager em;

    public CategoryRepository(EntityManager em) {
        this.em = em;
    }
    public int[] findIdByTitle(String name){
        TypedQuery<Category> query = em.createQuery("SELECT u FROM Category u WHERE u.title LIKE :name", Category.class);
        query.setParameter("name", "%" + name + "%");
        List<Category> category = query.getResultList();
        int[] id = new int[0];
        for(int i = 0; i<category.size(); i++){
            id[i] = category.get(i).getId();
        }
        return id;
    }
}
