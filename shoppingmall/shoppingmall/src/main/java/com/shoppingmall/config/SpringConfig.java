package com.shoppingmall.config;

import com.shoppingmall.policy.DiscountPolicy;
import com.shoppingmall.policy.FixDiscountPolicy;
import com.shoppingmall.repository.*;
import com.shoppingmall.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringConfig {
    @PersistenceContext
    public EntityManager em;
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
    public ProductRepository productRepository() { return new MemoryProductRepository(em); };
    @Bean
    public ProductService productService() { return new ProductService(productRepository(), fileStorageService(), categoryRepository()); }

    @Bean
    public CartRepository cartRepository() { return new CartRepository(em); }

    @Bean
    public CartService cartService() { return new CartService(cartRepository(), userRepository(), productRepository()); }

    @Bean
    public DiscountPolicy discountPolicy() { return new FixDiscountPolicy();}

    @Bean
    public SellerRepository sellerRepository() { return new SellerRepository(em); }

    @Bean
    public SellerService sellerService() { return new SellerService(sellerRepository()); }

    @Bean
    public CouponRepository couponRepository() { return new CouponRepository(em); }

    @Bean
    public CouponService couponService() { return new CouponService(couponRepository());}

    @Bean
    public WishlistRepository wishlistRepository() { return new WishlistRepository(); }

    @Bean
    public WishlistService wishlistService() { return new WishlistService(wishlistRepository()); }

    @Bean
    public PurchasesRepository purchasesRepository() { return new PurchasesRepository(em); }

    @Bean
    public PurchaseService purchaseService() { return new PurchaseService(purchasesRepository()); }

    @Bean
    public ReviewRepository reviewRepository() { return new ReviewRepository(em); }

    @Bean
    public ReviewService reviewService() { return new ReviewService(reviewRepository(), fileStorageService()); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/json/**").permitAll() // CSS 파일에 대한 접근을 허용
                        .requestMatchers("/user/status", "/products/add", "/cart/**", "/seller**", "/**Coupon","/pay", "/**Wishlist").authenticated()
                        .requestMatchers("/admin/**").hasRole("admin")
                        .anyRequest().permitAll())
                .exceptionHandling(ex -> ex
                .accessDeniedPage("/") // 접근 거부 페이지 설정
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("loginId")	// [C] submit할 아이디
                        .passwordParameter("password")
                        .permitAll()
                        .defaultSuccessUrl("/") // 성공 시 리다이렉트 URL
                        .failureUrl("/login?error") // 실패 시 리다이렉트 URL
                )
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().newSession() // 세션 고정 보호
                )
                .httpBasic(Customizer.withDefaults())
                .logout((logout) -> logout
                        .logoutUrl("/logout") // 로그아웃
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));// 세션 무효화

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    //UsernameAuthrctionfilter, Authmanager가 구현되어야 하는 것 같음
    //POST는 들어오지만 인증이 수행되지 않아서 에러가 발생하는 것 같음


}
