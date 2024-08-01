package com.shoppingmall.repository;

import com.shoppingmall.dto.Level;
import com.shoppingmall.domain.Users;

public interface UserRepository {

    Users add(Users user);

    Level valueOf(int value);

    Users findById(String id);

    Users findByEmail(String email);

}
