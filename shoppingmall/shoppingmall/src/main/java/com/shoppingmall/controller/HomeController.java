package com.shoppingmall.controller;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.domain.Wishlist;
import com.shoppingmall.dto.ProductDto;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.UserService;
import com.shoppingmall.service.WishlistService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    private final UserService userService;

    private final WishlistService wishlistService;

    public HomeController(ProductService productService, UserService userService, WishlistService wishlistService) {
        this.productService = productService;
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @GetMapping(value = "/")
    public String home(@AuthenticationPrincipal UserDetails userDetails,
                       Model model){

        //할인하는 상품 리스트 추출
        List<Products> products = productService.getDiscountProduct();
        if(products == null) products = new ArrayList<>();
        List<ProductDto> discountProductDtos = new ArrayList<>();
        Collections.sort(products, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return (int) (o2.getDiscount() - o1.getDiscount()); // 역순 정렬
            }
        });
        for (Products product : products) {
            int price = product.getPrice();
            int discountPrice = (int) (price * (1 - product.getDiscount() / 100));
            boolean isWishlist = false;
            int wishlistCnt = wishlistService.getAllWishlistByProductId(product.getId()).size();
            if (userDetails != null) {
                Wishlist wishlist = wishlistService.findByTwoId(userDetails.getUsername(), product.getId());
                if (wishlist != null) isWishlist = true;
            }
            discountProductDtos.add(new ProductDto(product, (int) product.getDiscount(), discountPrice, isWishlist, wishlistCnt));
        }

        //사용자 연령,성별에 맞는 상품 추천하기
        List<Products> products1 = null;
        int age = 0;
        String sex ="";
        if(userDetails != null){
            Users user = userService.findById(userDetails.getUsername());
            // 현재 날짜와의 차이를 계산하여 나이 구하기
            age = Period.between(user.getBirth(), LocalDate.now()).getYears();
            age = age/10 * 10;
            sex = user.getSex().equals("Male")? "남성":"여성";
            products1 = productService.getProductsByUserInfo(age, user.getSex());

        }
        if(products1 == null) products1 = new ArrayList<>();
        List<ProductDto> recommendProductDtos = new ArrayList<>();
        for (Products product : products1) {
            int price = product.getPrice();
            int discountPrice = (int) (price * (1 - product.getDiscount() / 100));
            boolean isWishlist = false;
            int wishlistCnt = wishlistService.getAllWishlistByProductId(product.getId()).size();
            if (userDetails != null) {
                Wishlist wishlist = wishlistService.findByTwoId(userDetails.getUsername(), product.getId());
                if (wishlist != null) isWishlist = true;
            }
            recommendProductDtos.add(new ProductDto(product, (int) product.getDiscount(), discountPrice, isWishlist, wishlistCnt));
        }
        model.addAttribute("discountProductDtos", discountProductDtos);
        model.addAttribute("recommendProductDtos", recommendProductDtos);
        model.addAttribute("age", age);
        model.addAttribute("sex",sex);
        return "index";
    }
}
