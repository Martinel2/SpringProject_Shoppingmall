package com.shoppingmall.User.Repository;

import com.shoppingmall.User.Enum.Level;
import com.shoppingmall.User.Domain.Users;

public interface UserRepository {

    Users add(Users user);

    Level valueOf(int value);

    Users findById(String id);

    Users findByEmail(String email);

    boolean deleteUser(Users users);

}
