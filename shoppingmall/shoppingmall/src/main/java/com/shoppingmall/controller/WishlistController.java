package com.shoppingmall.controller;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.domain.Wishlist;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.UserService;
import com.shoppingmall.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    private final UserService userService;

    private final ProductService productService;

    public WishlistController(WishlistService wishlistService, UserService userService, ProductService productService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/addWishlist")
    public ResponseEntity<String> addToWishlist(@RequestParam("productId") int productId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity response;
        try {
            String userId = userDetails.getUsername();
            Wishlist wishlist = wishlistService.findByTwoId(userId,productId);
            if(wishlist == null){
                Users user = userService.findById(userId);
                Products products = productService.findById(productId);
                wishlistService.save(user,products);
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("상품이 위시리스트에 추가되었습니다.");
            }
            else{
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("이미 찜한 상품");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @PostMapping("/delWishlist")
    public ResponseEntity<String> delToWishlist(@RequestParam("productId") int productId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity response;
        try {
            String userId = userDetails.getUsername();
            Wishlist wishlist = wishlistService.findByTwoId(userId,productId);
            if(wishlist != null){
                wishlistService.deleteWishlist(wishlist.getId());
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("상품이 삭제되었습니다.");
            }
            else{
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("제거실패");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }
}
