package com.shoppingmall;

import com.shoppingmall.repository.*;
import com.shoppingmall.service.CartService;
import com.shoppingmall.service.FileStorageService;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringConfig {
    @PersistenceContext
    private final EntityManager em;

    private final PasswordEncoder passwordEncoder;

    public SpringConfig(EntityManager em, PasswordEncoder passwordEncoder) {
        this.em = em;
        this.passwordEncoder = passwordEncoder;
    }

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
        return new UserService(userRepository(), passwordEncoder);
    }

    @Bean
    public ProductRepository productRepository() { return new MemoryProductRepository(em); };
    @Bean
    public ProductService productService() { return new ProductService(productRepository(), fileStorageService(), categoryRepository()); }

    @Bean
    public CartRepository cartRepository() { return new CartRepository(em); }

    @Bean
    public CartService cartService() { return new CartService(cartRepository(), userRepository(), productRepository()); }

}
