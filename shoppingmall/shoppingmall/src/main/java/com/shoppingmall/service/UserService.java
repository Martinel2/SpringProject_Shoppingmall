package com.shoppingmall.service;

import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //사용자 추가 코드
    public String join(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.add(user);
        return user.getId();
    }

    //중복 id 검사 코드
    public Users findById(String id) {
        return userRepository.findById(id);
    }

    //로그인 절차 확인 코드

    public Users Login(String id, String password){
        Users user = userRepository.findById(id);
        if(user==null) return null;
        else if(user.getPassword().equals(passwordEncoder.encode(user.getPassword()))) return user;
        return null;
    }


}
