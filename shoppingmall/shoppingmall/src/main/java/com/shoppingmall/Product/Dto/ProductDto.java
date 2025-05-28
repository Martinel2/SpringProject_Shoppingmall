package com.shoppingmall.Product.Dto;

import com.shoppingmall.Product.Domain.Products;

public class ProductDto {

    private Products products;

    private int percent;
    private int discountPrice;

    private boolean isWishlist;

    private int wishlistCnt;

    public ProductDto(Products products, int percent, int discountPrice, boolean isWishlist, int wishlistCnt) {
        this.products = products;
        this.percent = percent;
        this.discountPrice = discountPrice;
        this.isWishlist = isWishlist;
        this.wishlistCnt = wishlistCnt;
    }

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

    public boolean isWishlist() {
        return isWishlist;
    }

    public void setWishlist(boolean wishlist) {
        isWishlist = wishlist;
    }

    public int getWishlistCnt() {
        return wishlistCnt;
    }

    public void setWishlistCnt(int wishlistCnt) {
        this.wishlistCnt = wishlistCnt;
    }
}
