package com.shoppingmall.service;

import com.shoppingmall.domain.LoginRequest;
import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.UserRepository;
import jakarta.transaction.Transactional;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //사용자 추가 코드
    public String join(Users user){
        userRepository.add(user);
        return user.getId();
    }

    //중복 id 검사 코드
    public Users findById(String id) {
        return userRepository.findById(id);
    }

    //로그인 절차 확인 코드

    public Users Login(LoginRequest loginRequest){
        String id = loginRequest.getId();
        String password = loginRequest.getPassword();
        Users user = userRepository.findById(id);
        if(user==null) return null;
        else if(user.getPassword().equals(password)) return user;
        return null;
    }


}
