package com.shoppingmall;

import com.shoppingmall.repository.*;
import com.shoppingmall.service.FileStorageService;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.UserService;

import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;

@Configuration
public class SpringConfig {
    @PersistenceContext
    private final EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public ProductRepository productRepository() { return new MemoryProductRepository(em); };

    @Bean
    public FileStorageService fileStorageService() { return new FileStorageService(); };

    @Bean
    public CategoryRepository categoryRepository() { return  new CategoryRepository(em); };

    @Bean
    public UserRepository userRepository(){
        return new MemoryUserRepository(em);
    }
    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public ProductService productService() { return new ProductService(productRepository(), fileStorageService(), categoryRepository()); }
}
