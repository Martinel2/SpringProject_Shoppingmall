package com.shoppingmall.Service;

import com.shoppingmall.Product.Service.FileStorageService;
import com.shoppingmall.Product.Service.ProductService;
import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.Product.Repository.CategoryRepository;
import com.shoppingmall.Product.Repository.MemoryProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProductServiceTest {

    MemoryProductRepository productRepository;
    ProductService productService;
    CategoryRepository categoryRepository;
    private final EntityManager em;

    private final FileStorageService fileStorageService;  // 파일 저장 서비스



    public ProductServiceTest(EntityManager em, FileStorageService fileStorageService) {
        this.em = em;
        this.fileStorageService = fileStorageService;
    }

    @BeforeEach
    public void beforeEach() {
        productRepository = new MemoryProductRepository(em);
        categoryRepository = new CategoryRepository(em);
        productService = new ProductService(productRepository, fileStorageService, categoryRepository);
    }

    @Test
    public void getAllProduct(){
        List<Products> productsList = productService.getAllProduct();
        System.out.print(productsList.size());
    }
}
