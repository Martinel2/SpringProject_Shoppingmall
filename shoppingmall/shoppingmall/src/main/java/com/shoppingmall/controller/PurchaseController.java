package com.shoppingmall.controller;

import com.shoppingmall.domain.Purchases;
import com.shoppingmall.domain.Users;
import com.shoppingmall.service.PurchaseService;
import com.shoppingmall.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final UserService userService;
    public PurchaseController(PurchaseService purchaseService, UserService userService) {
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @GetMapping("/user/orderHistory")
    public String orderHistoryPage(@RequestParam(name = "startDate") String start,
                                   @RequestParam(name = "endDate") String end,
                                   @RequestParam(name = "keyword", required = false) String keyword,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model){

        Users users = userService.findById(userDetails.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 문자열을 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);

        // LocalDate를 LocalDateTime으로 변환하여 하루의 시작과 끝을 나타냄
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<Purchases> purchasesList = new ArrayList<>();

        if(keyword !=null && keyword.length() > 0) purchasesList = purchaseService.getPurchaseTermPlusKeyword(userDetails.getUsername(), startDateTime, endDateTime, keyword);

        else purchasesList = purchaseService.getPurchaseTerm(userDetails.getUsername(), startDateTime, endDateTime);

        // 날짜 기준으로 역순 정렬
        Collections.sort(purchasesList, new Comparator<Purchases>() {
            @Override
            public int compare(Purchases o1, Purchases o2) {
                return o2.getCreated().compareTo(o1.getCreated()); // 역순 정렬
            }
        });

        model.addAttribute("purchases",purchasesList);
        model.addAttribute("user", users);
        return "/user/orderHistory";
    }
}
