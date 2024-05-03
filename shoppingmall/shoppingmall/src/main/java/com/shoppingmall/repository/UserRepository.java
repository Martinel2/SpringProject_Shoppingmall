package com.shoppingmall.repository;

import com.shoppingmall.domain.Level;
import com.shoppingmall.domain.User;

import java.util.Optional;

public interface UserRepository {

    User add(User user);

    Level valueOf(int value);

    User findById(String id);

}
