package com.shoppingmall.policy;

import com.shoppingmall.User.Domain.Users;

public interface DiscountPolicy {

    int levelDiscount(Users user, int price);

    int productDiscount(int price, int percent);
}
