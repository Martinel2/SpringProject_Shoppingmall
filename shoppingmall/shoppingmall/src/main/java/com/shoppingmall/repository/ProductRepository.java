package com.shoppingmall.repository;
import com.shoppingmall.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository {
    Products save(Products product);
}

