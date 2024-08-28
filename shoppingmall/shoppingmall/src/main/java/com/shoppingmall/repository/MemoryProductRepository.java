package com.shoppingmall.repository;

import com.shoppingmall.domain.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MemoryProductRepository implements ProductRepository{
    @PersistenceContext
    public EntityManager em;

    public MemoryProductRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Products save(Products products) {
        em.persist(products);
        return products;
    }

    public Products findById(int id){
        return em.find(Products.class, id);
    }

    @Override
    public List<Products> findByName(String name){
        TypedQuery<Products> query = em.createQuery("SELECT u FROM Products u WHERE u.product_name LIKE :name", Products.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public List<Products> findByCategory(int category) {
        TypedQuery<Products> query = em.createQuery("SELECT u FROM Products u WHERE u.category = :category", Products.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<Products> findBySellerId(String seller_id) {
        TypedQuery<Products> query = em.createQuery("SELECT u FROM Products u WHERE u.seller_id like :seller_id", Products.class);
        query.setParameter("seller_id",  "%" + seller_id + "%");
        return query.getResultList();
    }

    @Override
    public boolean updateProduct(Products products) {
        if (products != null) {
            em.merge(products);
            return true;
        }
        return false;
    }

    @Override
    public boolean discount(int id, int discount) {
        Products product = em.find(Products.class, id);
        if (product != null) {
            product.setDiscount(discount);
            // `merge` 메서드를 사용하여 업데이트 수행
            em.merge(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int id) {
        Products product = em.find(Products.class, id);
        if (product != null) {
            em.remove(product); // 엔티티 삭제
            return true;
        }
        return false;
    }

    @Override
    public List<Products> getAllProduct() {
        String query = "SELECT c FROM Products c";
        return em.createQuery(query, Products.class).getResultList();
    }
}
