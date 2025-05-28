package com.shoppingmall.User.Controller;

import com.shoppingmall.User.Domain.Complaint;
import com.shoppingmall.Purchase.Domain.Purchases;
import com.shoppingmall.Review.Domain.Review;
import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Purchase.Dto.PurchaseDto;
import com.shoppingmall.User.Service.ComplaintService;
import com.shoppingmall.Purchase.Service.PurchaseService;
import com.shoppingmall.Review.Service.ReviewService;
import com.shoppingmall.User.Service.UserService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime bef = LocalDate.now().atTime(00,00,00).minusMonths(1);
        List<Purchases> purchases = purchaseService.getPurchaseTerm(users.getId(), bef, now);
        // 날짜 기준으로 역순 정렬
        Collections.sort(purchases, new Comparator<Purchases>() {
            @Override
            public int compare(Purchases o1, Purchases o2) {
                return o2.getCreated().compareTo(o1.getCreated()); // 역순 정렬
            }
        });

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
