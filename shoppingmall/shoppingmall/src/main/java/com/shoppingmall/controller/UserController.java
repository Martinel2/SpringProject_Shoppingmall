package com.shoppingmall.controller;

import com.shoppingmall.domain.Purchases;
import com.shoppingmall.domain.Review;
import com.shoppingmall.domain.Users;
import com.shoppingmall.dto.PurchaseDto;
import com.shoppingmall.service.PurchaseService;
import com.shoppingmall.service.ReviewService;
import com.shoppingmall.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PurchaseService purchaseService;

    private final ReviewService reviewService;

    public UserController(UserService userService, PurchaseService purchaseService, ReviewService reviewService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.reviewService = reviewService;
    }


    // 회원가입 창 매핑
    @GetMapping(value = "/user/new")
    public String createForm() {
        return "user/createUserForm";
    }

    // 회원가입 완료 페이지 매핑
    @GetMapping(value = "/user/complete")
    public String complete(@RequestParam(value = "id", required = false) String id, Model model) {
        model.addAttribute("id", id);
        return "user/complete";
    }

    @GetMapping("/user/status")
    public String profilePage(@AuthenticationPrincipal User user, Model model) {
        Users users = userService.findById(user.getUsername());
        List<Purchases> purchases = purchaseService.getPurchaseWithinOneMonth(users.getId());
        List<Review> reviews = reviewService.findReviewByUserId(users.getId());
        List<PurchaseDto> purchaseDtos= new ArrayList<>();
        for(Purchases p : purchases){
            boolean canReview = true;
            for(Review r : reviews){
                if(p.getId() == r.getPurchases().getId()){
                    canReview = false;
                    break;
                }
            }
            purchaseDtos.add(new PurchaseDto(p,canReview));
        }
        model.addAttribute("user", users);
        model.addAttribute("purchaseDto", purchaseDtos);
        return "/user/status";
    }

    @GetMapping("/user/info")
    public String dashboardPage(@AuthenticationPrincipal User user, Model model) {
        Users users = userService.findById(user.getUsername());
        model.addAttribute("user", users);
        return "/user/info";
    }

}
