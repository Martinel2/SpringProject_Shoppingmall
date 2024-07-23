package com.shoppingmall;

import com.shoppingmall.repository.MemoryProductRepository;
import com.shoppingmall.repository.MemoryUserRepository;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.repository.UserRepository;
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
    public UserRepository userRepository(){
        return new MemoryUserRepository(em);
    }
    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }
}
