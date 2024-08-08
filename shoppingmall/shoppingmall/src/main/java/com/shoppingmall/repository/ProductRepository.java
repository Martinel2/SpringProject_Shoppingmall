package com.shoppingmall.repository;
import com.shoppingmall.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository {
    Products save(Products product);

    List<Products> findByName(String name);

    List<Products> findByCategory(int category);

    List<Products> findBySellerId(String seller_id);

    Products findById(int productId);

    boolean updatePrice(int id, int price);

    boolean discount(int id, int discount);

    boolean deleteProduct(int id);

    List<Products> getAllProduct();
}

