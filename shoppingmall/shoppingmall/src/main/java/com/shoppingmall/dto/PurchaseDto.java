package com.shoppingmall.dto;

import com.shoppingmall.domain.Purchases;

public class PurchaseDto {

    Purchases purchases;

    boolean CanReview;

    public PurchaseDto(Purchases purchases, boolean canReview) {
        this.purchases = purchases;
        CanReview = canReview;
    }

    public Purchases getPurchases() {
        return purchases;
    }

    public void setPurchases(Purchases purchases) {
        this.purchases = purchases;
    }

    public boolean isCanReview() {
        return CanReview;
    }

    public void setCanReview(boolean canReview) {
        CanReview = canReview;
    }
}
