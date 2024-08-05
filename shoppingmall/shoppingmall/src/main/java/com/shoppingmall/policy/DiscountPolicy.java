package com.shoppingmall.policy;

import com.shoppingmall.domain.Users;

public interface DiscountPolicy {

    int levelDiscount(Users user, int price);

    int productDiscount(int price, int percent);
}
