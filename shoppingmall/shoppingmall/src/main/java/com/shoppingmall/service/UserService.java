package com.shoppingmall.service;

import com.shoppingmall.domain.User;
import com.shoppingmall.repository.MemoryUserRepository;
import com.shoppingmall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public String join(User user){
        validateDupUser(user); //아이디가 중복인지 검증
        userRepository.add(user);
        return user.getId();
    }

    public void validateDupUser(User user){
        userRepository.findById(user.getId())
                .ifPresent(u ->{
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }
}
