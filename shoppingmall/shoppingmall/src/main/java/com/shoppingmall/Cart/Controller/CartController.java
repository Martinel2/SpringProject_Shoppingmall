package com.shoppingmall.Cart.Controller;

import com.shoppingmall.Cart.Domain.Cart;
import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.Cart.Service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String getCart(@AuthenticationPrincipal UserDetails userDetails,
                          Model model){
        List<Cart> carts = new ArrayList<>();
        carts = cartService.getCartByUserId(userDetails.getUsername());
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
    public ResponseEntity<String> addToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity response;
        try {
            String userId = userDetails.getUsername();
            Cart cart = cartService.findByTwoId(userId,productId);
            if(cart == null){
                cartService.addCart(userId, productId, quantity); // 장바구니에 상품 추가
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("상품이 장바구니에 추가되었습니다.");
            }
            else{
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("이미 추가된 상품입니다");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @PostMapping("/cart/update")
    public ResponseEntity<String> updateCartItemQuantity(@RequestParam(value = "cartItemId") int cartItemId,
                                         @RequestParam(value = "quantity") int quantity) {
        ResponseEntity response = null;
        try {
            cartService.updateCartItemQuantity(cartItemId, quantity);
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("success");
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @PostMapping("cart/delete")
    public ResponseEntity<String> deleteCartItem(@RequestParam(value = "cartItemId") int cartItemId) {
        ResponseEntity response = null;
        try {
            cartService.deleteCartItem(cartItemId);
            response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("success");
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }
}
