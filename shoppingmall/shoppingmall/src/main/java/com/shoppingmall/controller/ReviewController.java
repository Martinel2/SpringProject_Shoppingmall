package com.shoppingmall.controller;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.ReviewService;
import com.shoppingmall.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ReviewController {

    private final UserService userService;
    private final ProductService productService;
    private final ReviewService reviewService;

    public ReviewController(UserService userService, ProductService productService, ReviewService reviewService) {
        this.userService = userService;
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping("/reviewPopup")
    public String openReviewPopup(@RequestParam("itemId") Long itemId, Model model) {
        // itemId를 이용해 아이템 정보를 가져와서 모델에 추가
        model.addAttribute("itemId", itemId);
        // 필요한 다른 정보들도 모델에 추가
        return "/review"; // 팝업 창에 표시할 HTML 파일 이름
    }

    @PostMapping("/submitReview")
    public ResponseEntity<String> submitReview(@RequestParam("itemId") int itemId,
                                               @RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               @RequestParam("rating") int rating,
                                               @RequestParam("photo") MultipartFile photo,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity response = null;
        try {
            // 리뷰 저장 로직 구현
            Users users = userService.findById(userDetails.getUsername());
            Products products = productService.findById(itemId);
            reviewService.writeReview(users, products, title, content, rating, photo);
            response = ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("리뷰가 등록되었습니다!");
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }
}
