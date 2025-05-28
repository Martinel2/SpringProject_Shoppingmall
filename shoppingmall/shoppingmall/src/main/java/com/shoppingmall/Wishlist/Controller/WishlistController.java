package com.shoppingmall.Wishlist.Controller;

import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Wishlist.Domain.Wishlist;
import com.shoppingmall.Product.Service.ProductService;
import com.shoppingmall.User.Service.UserService;
import com.shoppingmall.Wishlist.Service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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
                        .body("success");
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
    public ResponseEntity<Map<String, Object>> delToWishlist(@RequestParam("productId") int productId,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity response;
        try {
            String userId = userDetails.getUsername();
            Wishlist wishlist = wishlistService.findByTwoId(userId,productId);
            if(wishlist != null){
                wishlistService.deleteWishlist(wishlist.getId());
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("success");
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

    @GetMapping("/user/wishlist")
    public String wishlistPage(@AuthenticationPrincipal UserDetails userDetails,
                               Model model){
        Users users = userService.findById(userDetails.getUsername());
        List<Wishlist> wishlists = wishlistService.getAllWishlistByUserId(users.getId());
        model.addAttribute("user", users);
        model.addAttribute("wishlists", wishlists);

        return "/user/wishlist";
    }
}
