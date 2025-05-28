package com.shoppingmall.User.Controller;

import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.User.Domain.Seller;
import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Product.Service.ProductService;
import com.shoppingmall.User.Service.SellerService;
import com.shoppingmall.User.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SellerController {

    private final ProductService productService;

    private final SellerService sellerService;

    private final UserService userService;

    public SellerController(ProductService productService, SellerService sellerService, UserService userService) {
        this.productService = productService;

        this.sellerService = sellerService;
        this.userService = userService;
    }

    @GetMapping(value = "/products/add")
    public String createForm(@AuthenticationPrincipal User user, Model model) {
        String userId = user.getUsername();
        Seller seller = sellerService.findById(userId);
        boolean isSeller = false;
        if(seller != null) {
            isSeller = true;
        }

        model.addAttribute("isSeller", isSeller);
        return "/product/productAdd";
    }


    @GetMapping("/sellerStatus")
    public String sellerStatusPage(@AuthenticationPrincipal User user, Model model){
        String userId = user.getUsername();
        Seller seller = sellerService.findById(userId);
        List<Products> products = new ArrayList<>();
        boolean isSeller = false;
        if(seller != null) {
            isSeller = true;
            products = productService.searchBySellerId(userId);
        }

        model.addAttribute("isSeller", isSeller);
        model.addAttribute("userId", userId);
        model.addAttribute("products", products);
        return "/user/sellerStatus";
    }

    @GetMapping("/sellerSignup")
    public String sellerSignupPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("id", user.getUsername());
        return "/user/sellerSignup";
    }

    @PostMapping("/sellerSignup")
    public ResponseEntity<String> sellerSignup(@AuthenticationPrincipal User user,
                                               @RequestParam(name = "company_id") String company_id,
                                               @RequestParam(name = "company_name") String company_name){
        ResponseEntity response = null;
        try {
            Users users = userService.findById(user.getUsername());
            sellerService.join(users, company_id, company_name);
            response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("판매자 계정 가입이 완료되었습니다.");
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

}
