package com.shoppingmall.service;

import com.shoppingmall.domain.LoginRequest;
import com.shoppingmall.domain.User;
import com.shoppingmall.repository.MemoryUserRepository;
import com.shoppingmall.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Console;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //사용자 추가 코드
    public String join(User user){
        userRepository.add(user);
        return user.getId();
    }

    //중복 id 검사 코드
    public boolean isIdExists(String id) {
        User user = userRepository.findById(id);
        return user != null;
    }

    //로그인 절차 확인 코드
    public boolean Login(LoginRequest loginRequest){
        String id = loginRequest.getId();
        String password = loginRequest.getPassword();
        User user = userRepository.findById(id);
        if(user==null) return false;
        if(user.getPassword().equals(password)) return true;
        else return false;
    }
}
