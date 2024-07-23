package com.shoppingmall.repository;

import com.shoppingmall.domain.Level;
import com.shoppingmall.domain.Users;

import java.util.Optional;

public interface UserRepository {

    Users add(Users user);

    Level valueOf(int value);

    Users findById(String id);

}
