package com.shoppingmall;

import com.shoppingmall.repository.MemoryUserRepository;
import com.shoppingmall.repository.UserRepository;
import com.shoppingmall.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public UserRepository UserRepository(){
        return new MemoryUserRepository();
    }
    @Bean
    public UserService userService(UserRepository userRepository){
        return new UserService(userRepository);
    }
}
