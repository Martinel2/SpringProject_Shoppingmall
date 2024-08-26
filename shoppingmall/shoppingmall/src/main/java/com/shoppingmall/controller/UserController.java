package com.shoppingmall.controller;

import com.shoppingmall.domain.Complaint;
import com.shoppingmall.domain.Purchases;
import com.shoppingmall.domain.Review;
import com.shoppingmall.domain.Users;
import com.shoppingmall.dto.PurchaseDto;
import com.shoppingmall.service.ComplaintService;
import com.shoppingmall.service.PurchaseService;
import com.shoppingmall.service.ReviewService;
import com.shoppingmall.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PurchaseService purchaseService;

    private final ReviewService reviewService;

    private final ComplaintService complaintService;

    public UserController(UserService userService, PurchaseService purchaseService, ReviewService reviewService, ComplaintService complaintService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.reviewService = reviewService;
        this.complaintService = complaintService;
    }


    // 회원가입 창 매핑
    @GetMapping(value = "/newUser")
    public String createForm() {
        return "user/createUserForm";
    }

    // 회원가입 완료 페이지 매핑
    @GetMapping(value = "/newUserComplete")
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

    @GetMapping("/user/quit")
    public String quitPage(){
        return "/user/quit";
    }

    @PostMapping("/quitUser")
    public ResponseEntity<String> quitUser( HttpServletRequest request,
                                            @RequestParam(name = "reason") String reason,
                                            @RequestParam(name = "additional_comments") String comments,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity response;
        try {
                Users users = userService.findById(userDetails.getUsername());
                if(userService.delete(users)){
                    Complaint complaint = new Complaint();
                    complaint.setReason(reason);
                    complaint.setComments(comments);
                    complaintService.addComplaint(complaint);
                    response = ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body("탈퇴가 완료되었습니다.");
                    // 세션 무효화 및 로그아웃
                    SecurityContextHolder.clearContext();
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                }
                else{
                    response = ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body("이미 탈퇴된 아이디입니다.");
                }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @GetMapping("/quitComplete")
    private String quitCompletePage(){
        return "/user/quitComplete";
    }

}
