package com.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingmall.domain.Coupon;
import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Purchases;
import com.shoppingmall.domain.Users;
import com.shoppingmall.service.CartService;
import com.shoppingmall.service.CouponService;
import com.shoppingmall.service.PurchaseService;
import com.shoppingmall.service.UserService;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Controller
public class WidgetController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    private final CartService cartService;

    private final PurchaseService purchaseService;

    private final CouponService couponService;

    public WidgetController(UserService userService, CartService cartService, PurchaseService purchaseService, CouponService couponService) {
        this.userService = userService;
        this.cartService = cartService;
        this.purchaseService = purchaseService;
        this.couponService = couponService;
    }

    @GetMapping("/pay")
    public String payPage(@AuthenticationPrincipal UserDetails userDetail, Model model){
        Users users = userService.findById(userDetail.getUsername());
        model.addAttribute("user", users);
        return "/toss/checkout";
    }

    @GetMapping("/success")
    public String success() {
        return "/toss/success";
    }

    @GetMapping("/fail")
    public String fail() {
        return "/toss/fail";
    }

    @Transactional
    @RequestMapping(value = "/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody, @AuthenticationPrincipal UserDetails userDetails) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        String paymentType;

        JSONArray cartIdsJsonArray;
        JSONArray couponIdsJsonArray;
        JSONArray quantityJsonArray;
        JSONArray priceJsonArray;

        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
            cartIdsJsonArray = (JSONArray) requestData.get("cartIds");
            couponIdsJsonArray = (JSONArray) requestData.get("couponIds");
            quantityJsonArray = (JSONArray) requestData.get("quantity");
            priceJsonArray = (JSONArray) requestData.get("price");
            paymentType = (String) requestData.get("paymentType");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ;
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // JSONArray를 int[] 배열로 변환
        int[] cartIds = new int[cartIdsJsonArray.size()];
        int[] couponIds = new int[couponIdsJsonArray.size()];
        int[] quantity = new int[quantityJsonArray.size()];
        int[] price = new int[priceJsonArray.size()];

        for (int i = 0; i < cartIdsJsonArray.size(); i++) {
            cartIds[i] = Integer.parseInt((String) cartIdsJsonArray.get(i));
            couponIds[i] = Integer.parseInt((String) couponIdsJsonArray.get(i));
            quantity[i] = Integer.parseInt((String) quantityJsonArray.get(i));
            price[i] = Integer.parseInt((String) priceJsonArray.get(i));
        }

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = "결제 연동 시크릿 키";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        int len = cartIds.length;
        String userId = userDetails.getUsername();
        for(int i = 0; i<len; i++){
            Coupon coupon = couponService.findCouponById(couponIds[i]);
            Purchases purchases = new Purchases();
            purchases.setUser_id(userId);
            purchases.setUsers(userService.findById(userId));
            Products p = cartService.findById(cartIds[i]).getProducts();
            purchases.setProduct_id(p.getId());
            purchases.setPurchase_type(paymentType);
            purchases.setProducts(p);
            purchases.setCoupon(coupon);
            purchases.setProduct_cnt(quantity[i]);
            purchases.setPrice(price[i]);
            purchases.setOrder_id(orderId);
            purchaseService.addPurchase(purchases);
            cartService.deleteCartItem(cartIds[i]);
            if(coupon.getId() != 0)
                couponService.deleteFirstCouponList(userId,couponIds[i]);

        }
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);
    }

    @Transactional
    @GetMapping("/researchOrder")
    public String research(@RequestParam(name = "purchase_id") int purchase_id,
                                               Model model) throws Exception {

        Purchases purchases = purchaseService.findById(purchase_id);

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = "결제 연동 시크릿 키";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/orders/" + purchases.getOrder_id());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestMethod("GET");

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> paymentData = objectMapper.readValue(responseStream, Map.class);

        model.addAttribute("paymentData", paymentData);
        model.addAttribute("purchase", purchases);
        return "/toss/withdrawal";  // 타임리프 템플릿 이름
    }

    @PostMapping("/refund")
    public ResponseEntity<String> quitUser( @RequestParam(name = "cancelReason") String cancelReason,
                                            @RequestParam(name = "cancelAmount") String cancelAmount,
                                            @RequestParam(name = "taxFreeAmount") String taxFreeAmount,
                                            @RequestParam(name = "paymentKey") String paymentKey,
                                            @RequestParam(name = "purchase_id") int purchase_id) {
        ResponseEntity response;
        try {
            JSONObject obj = new JSONObject();
            obj.put("cancelReason", cancelReason);
            obj.put("cancelAmount", cancelAmount);
            obj.put("taxFreeAmount", taxFreeAmount);

            // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
            // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
            String widgetSecretKey = "결제 연동 시크릿 키";
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
            String authorizations = "Basic " + new String(encodedBytes);

            // 결제를 승인하면 결제수단에서 금액이 차감돼요.
            URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", authorizations);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(obj.toString().getBytes("UTF-8"));

            int code = connection.getResponseCode();
            boolean isSuccess = code == 200;

            if(isSuccess){
                Purchases purchases = purchaseService.findById(purchase_id);
                purchaseService.deletePurchase(purchases);
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("환불 완료");
            }
            else {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("환불 실패");

            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }
}
