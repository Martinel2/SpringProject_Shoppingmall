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
        // MemberRepository의 메서드를 사용하여 아이디가 존재하는지 확인합니다.
        // Member 엔티티 클래스의 필드 이름과 테이블 컬럼 이름이 같다고 가정합니다.
        User user = userRepository.findById(id);
        return user != null;
    }

}
