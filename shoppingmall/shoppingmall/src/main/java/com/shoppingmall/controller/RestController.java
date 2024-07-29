package com.shoppingmall.controller;

import com.shoppingmall.domain.LoginRequest;
import com.shoppingmall.domain.Users;
import com.shoppingmall.service.CartService;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserService userService;

    private final CartService cartService;
    private final ProductService productService;
    public RestController(UserService userService, CartService cartService, ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }


    //로그인 처리
    @PostMapping(value = "/user/login")
    public String handleLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        Users user = userService.Login(loginRequest);
        if(user != null){
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            return "success";
        }
        else return "fail";
    }

    // 회원가입 처리
    @PostMapping(value = "/user/new")
    public String handleRequest(@RequestBody Users user, RedirectAttributes redirectAttributes) {
        if (userService.isIdExists(user.getId()) == null) {
            userService.join(user);
            redirectAttributes.addAttribute("id", user.getId());
            return "success";
        }
        return "fail";
    }

    @PostMapping("/products/add")
    public String addProduct(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "price") int price,
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "category") int category,
            HttpSession session
    ) {
        String userId = (String) session.getAttribute("userId");
        productService.saveProduct(name, description, price, file, category,userId);
        return "success";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, HttpSession httpSession) {
        // 로그인 여부 확인
        if (httpSession.getAttribute("userId") == null) {
            return "not_logged_in"; // 로그인되지 않은 경우
        }

        String userId = (String) httpSession.getAttribute("userId");
        cartService.addCart(userId, productId, quantity); // 장바구니에 상품 추가
        return "success";
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public String updateCartItemQuantity(@RequestParam(value = "cartItemId") int cartItemId,
                                         @RequestParam(value = "quantity") int quantity) {
        cartService.updateCartItemQuantity(cartItemId, quantity);
        return "success";
    }

}
