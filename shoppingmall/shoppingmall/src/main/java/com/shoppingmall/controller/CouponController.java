package com.shoppingmall.controller;

import com.shoppingmall.domain.Coupon;
import com.shoppingmall.domain.Users;
import com.shoppingmall.dto.CouponDto;
import com.shoppingmall.dto.Level;
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

import java.time.LocalDate;
import java.util.ArrayList;
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

    @GetMapping("/couponPopup")
    public String getCoupons(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam(name = "price") int price,
                             @RequestParam(name = "useCouponId") int useCouponId,
                             @RequestParam(name = "cartItemId") int cartItemId,
                             @RequestParam(name = "usedCouponIds", required = false) List<Integer> usedCouponIds,
                             Model model) {
        List<Coupon> coupons = couponService.makeRealCouponList(couponService.findListByUserId(userDetails.getUsername()));
        List<Coupon> excludeCoupon = new ArrayList<>();
        Coupon useCoupon = null;
        if(usedCouponIds!=null && coupons != null){
            for (Coupon c: coupons) {
                boolean canUse = true;
                for (int id: usedCouponIds) {
                    if(c.getId() == useCouponId){
                        useCoupon = c;
                        break;
                    }

                    if(c.getId() == id) {
                        canUse = false;
                        break;
                    }
                }
                if(canUse) excludeCoupon.add(c);
            }
        }
        if(useCoupon == null){
            useCoupon = new Coupon();
            useCoupon.setId(0);
        }
        model.addAttribute("coupons", excludeCoupon);
        model.addAttribute("price", price);
        model.addAttribute("useCoupon", useCoupon);
        model.addAttribute("cartItemId", cartItemId);
        return "/cart/couponPopup"; // 200 OK 상태 코드와 함께 쿠폰 리스트 반환
    }

    @GetMapping("/event")
    public String eventCouponPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Users users = userService.findById(userDetails.getUsername());
        int level = Level.toInt(users.getRole());
        List<Coupon> coupons = couponService.findCouponByLevel(level);
        model.addAttribute("coupons", coupons);
        model.addAttribute("level", users.getRole());
        return "/event";
    }

    @GetMapping("/myCoupon")
    public String myCoupon(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Coupon> coupons = couponService.makeRealCouponList(couponService.findListByUserId(userDetails.getUsername()));
        Users users = userService.findById(userDetails.getUsername());
        List<CouponDto> couponDtos= new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (Coupon c: coupons) {
            int validDate = c.getValidTerm();
            LocalDate expired = now.plusDays(validDate);
            couponDtos.add(new CouponDto(c,expired));
        }
        model.addAttribute("user", users);
        model.addAttribute("couponDto", couponDtos);
        return "/user/coupon"; // 200 OK 상태 코드와 함께 쿠폰 리스트 반환
    }
}
