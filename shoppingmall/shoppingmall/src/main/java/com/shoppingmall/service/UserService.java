package com.shoppingmall.service;

import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //중복 id 검사 코드
    public Users findById(String id) {
        return userRepository.findById(id);
    }

    //로그인 절차 확인 코드

    /*public Users Login(String id, String password){
        Users user = userRepository.findById(id);
        if(user==null) return null;
        else if(user.getPassword().equals(user.getPassword())) return user;
        return null;
    }
     */

    @Override
    public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
        Optional<Users> findOne = Optional.ofNullable(userRepository.findById(insertedUserId));
        Users users = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));

        return User.builder()
                .username(users.getId())
                .password(users.getPassword())
                .roles(users.getRole())
                .build();
    }

    public Users join(Users users){
        userRepository.add(users);
        return users;
    }

}
