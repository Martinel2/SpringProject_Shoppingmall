package com.shoppingmall.Product.Repository;

import com.shoppingmall.Product.Domain.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Products> getDiscountProduct(){
        TypedQuery<Products> query = em.createQuery("SELECT u FROM Products u WHERE u.discount > 0", Products.class);
        return query.getResultList();
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

    @Override
    public List<Products> getProductsByUserInfo(int age, String sex){
        // 20년 전과 30년 전의 날짜를 계산하여 birth 조건으로 사용
        LocalDate startDate = LocalDate.now().minusYears(age+10); // age+10년 전
        LocalDate endDate = LocalDate.now().minusYears(age);   // age년 전

        TypedQuery<Object[]> query = em.createQuery(
                "SELECT p, COUNT(p) as cnt FROM Purchases r " +
                        "JOIN r.products p " +
                        "WHERE r.users.birth BETWEEN :startDate AND :endDate AND r.users.sex = :sex " +
                        "GROUP BY p " +
                        "ORDER BY cnt DESC",
                Object[].class
        );

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("sex", sex);

        List<Object[]> results = query.getResultList();

        // 가장 많이 등장한 순서대로 Products 리스트 추출
        List<Products> products = results.stream()
                .map(result -> (Products) result[0])
                .collect(Collectors.toList());
        return products;
    }
}
