package com.shoppingmall.controller;

import com.shoppingmall.domain.Cart;
import com.shoppingmall.domain.Products;
import com.shoppingmall.dto.CartResponseDto;
import com.shoppingmall.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/{user_id}")
    public String getCart(@PathVariable("user_id") String user_id,
                          Model model){
        List<Cart> carts = new ArrayList<>();
        carts = cartService.getCartByUserId(user_id);
        int total = 0;
        for(int i = 0; i<carts.size(); i++){
            Products p = carts.get(i).getProducts();
            total += (carts.get(i).getQuantity()*p.getPrice());
        }
        model.addAttribute("carts", carts);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/cart/add")
    public ResponseEntity<CartResponseDto> addToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, @AuthenticationPrincipal UserDetails userDetails) {
        CartResponseDto response;
        try {
            String userId = userDetails.getUsername();
            Cart cart = cartService.findByTwoId(userId,productId);
            if(cart == null){
                cartService.addCart(userId, productId, quantity); // 장바구니에 상품 추가
                response = new CartResponseDto("상품이 장바구니에 추가되었습니다.", userId);
                return ResponseEntity.ok(response); // 200 OK 응답 반환
            }
            else{
                response = new CartResponseDto("이미 추가된 상품입니다",null);
                return ResponseEntity.ok(response); // 200 OK 응답 반환
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CartResponseDto("상품 추가에 실패했습니다.", null)); // 500 응답 반환
        }
    }

    @PostMapping("/cart/update")
    public ResponseEntity<String> updateCartItemQuantity(@RequestParam(value = "cartItemId") int cartItemId,
                                         @RequestParam(value = "quantity") int quantity) {
        ResponseEntity response = null;
        try {
            cartService.updateCartItemQuantity(cartItemId, quantity);
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(Map.of("message", "success"));
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }
}
