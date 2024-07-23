package com.shoppingmall.controller;

import com.shoppingmall.domain.LoginRequest;
import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.UserService;
import com.shoppingmall.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserService userService;

    public RestController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private ProductService productService;

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

}
