package com.shoppingmall;

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
public class SpringSecurityConfig {

    // 로그인 기억하기 사용을 위해 MemberAuthenticatorProvider 내부
    // MemberPrincipalDetailsService 선언

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/json/**").permitAll() // CSS 파일에 대한 접근을 허용
                        .requestMatchers("/user/status","/products/add", "/cart/**").authenticated()
                        .requestMatchers("/user/login","/user/new","/", "/search/**", "/products/{id}").permitAll())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/user/login")
                        .usernameParameter("loginId")	// [C] submit할 아이디
                        .passwordParameter("password")
                        .permitAll()
                        .defaultSuccessUrl("/user/status") // 성공 시 리다이렉트 URL
                        .failureUrl("/login?error") // 실패 시 리다이렉트 URL
                )
                .httpBasic(Customizer.withDefaults())
                .logout((logout) -> logout
                        .logoutUrl("/user/status") // 로그아웃 성공 시 리다이렉트
                        .invalidateHttpSession(true)) // 세션 무효화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 상태를 Stateless로 설정

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }


}