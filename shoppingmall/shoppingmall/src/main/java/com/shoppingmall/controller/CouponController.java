package com.shoppingmall.controller;

import com.shoppingmall.domain.Coupon;
import com.shoppingmall.domain.Users;
import com.shoppingmall.service.CouponService;
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

import java.util.List;

@Controller
public class CouponController {

    private final CouponService couponService;
    private final UserService userService;

    public CouponController(CouponService couponService, UserService userService) {
        this.couponService = couponService;
        this.userService = userService;
    }

    @PostMapping("/saveCoupon")
    public ResponseEntity<String> useCoupon(@RequestParam(name = "coupon_id") int couponId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity response = null;
        try {
            Coupon coupon = couponService.findCouponById(couponId);
            Users user = userService.findById(userDetails.getUsername());
            if(couponService.saveCoupon(user,coupon)){
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("쿠폰이 발급되었습니다!");
            }
            else{
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("이미 발급된 쿠폰입니다.");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @GetMapping("/getCoupon")
    public ResponseEntity<List<Coupon>> getCoupons(@AuthenticationPrincipal UserDetails userDetails) {
        List<Coupon> coupons = couponService.makeRealCouponList(couponService.findListByUserId(userDetails.getUsername()));
        return ResponseEntity.ok(coupons); // 200 OK 상태 코드와 함께 쿠폰 리스트 반환
    }

    @GetMapping("/event")
    public String eventCouponPage(Model model){
        List<Coupon> coupons = couponService.getAllCoupon();
        model.addAttribute("coupons", coupons);
        return "/event";
    }



}
