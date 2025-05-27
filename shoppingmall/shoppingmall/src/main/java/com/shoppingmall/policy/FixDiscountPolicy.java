package com.shoppingmall.policy;

import com.shoppingmall.User.Domain.Users;

public class FixDiscountPolicy implements DiscountPolicy{
    @Override
    public int levelDiscount(Users user, int price) {
        return 0;
    }

    @Override
    public int productDiscount(int price, int percent) {
        int discountPrice = price*((100-percent)/100);
        return discountPrice;
    }
}
