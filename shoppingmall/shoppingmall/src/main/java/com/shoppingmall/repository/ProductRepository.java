package com.shoppingmall.repository;

import com.shoppingmall.domain.Products;

import java.util.List;

public interface ProductRepository {
    Products save(Products product);

    List<Products> findByName(String name);

    List<Products> findByCategory(int category);

    List<Products> findBySellerId(String seller_id);

    Products findById(int productId);

    boolean updateProduct(Products products);

    boolean discount(int id, int discount);

    boolean deleteProduct(int id);

    List<Products> getAllProduct();
}

