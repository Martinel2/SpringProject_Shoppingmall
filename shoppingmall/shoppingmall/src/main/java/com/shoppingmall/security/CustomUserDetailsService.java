package com.shoppingmall.security;

import com.shoppingmall.domain.Users;
import com.shoppingmall.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Users user = userService.findById(loginId);
        if(user == null) throw new UsernameNotFoundException("없는 회원입니다.");
        else if(user.getEnabled().equals("F")) throw new UsernameNotFoundException("비활성화된 계정입니다");

        return User.builder()
                .username(user.getId())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}

