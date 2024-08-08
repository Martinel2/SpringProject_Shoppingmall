package com.shoppingmall.dto;

import com.shoppingmall.domain.Products;

public class ProductDto {

    private Products products;

    private int percent;
    private int discountPrice;

    public ProductDto(Products product, int discountPrice) {
        this.products = product;
        this.percent = (int) product.getDiscount();
        this.discountPrice = discountPrice;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
