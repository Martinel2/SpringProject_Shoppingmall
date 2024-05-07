package com.shoppingmall.service;

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

    //회원가입
    public String join(User user){
        userRepository.add(user);
        return user.getId();
    }

    public boolean isIdExists(String id) {
        User user = userRepository.findById(id);
        return user != null;
    }

    public boolean Login(String id, String password){
        User user = userRepository.findById(id);
        if(user.getPassword().equals(password)) return true;
        else return false;
    }
}
