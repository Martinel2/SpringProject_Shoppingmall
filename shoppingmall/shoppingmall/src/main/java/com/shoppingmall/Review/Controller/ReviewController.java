package com.shoppingmall.Review.Controller;

import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.Purchase.Domain.Purchases;
import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Product.Service.ProductService;
import com.shoppingmall.Purchase.Service.PurchaseService;
import com.shoppingmall.Review.Service.ReviewService;
import com.shoppingmall.User.Service.UserService;
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

import java.util.List;

@Controller
public class ReviewController {

    private final UserService userService;
    private final PurchaseService purchaseService;
    private final ReviewService reviewService;
    private final ProductService productService;

    public ReviewController(UserService userService, PurchaseService purchaseService, ReviewService reviewService, ProductService productService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.reviewService = reviewService;
        this.productService = productService;
    }

    @GetMapping("/reviewPopup")
    public String openReviewPopup(@RequestParam("orderId") String orderId,
                                  @RequestParam("productId") int productId,
                                  Model model) {
        // itemId를 이용해 아이템 정보를 가져와서 모델에 추가
        model.addAttribute("orderId", orderId);
        model.addAttribute("productId", productId);
        // 필요한 다른 정보들도 모델에 추가
        return "/review"; // 팝업 창에 표시할 HTML 파일 이름
    }

    @PostMapping("/submitReview")
    public ResponseEntity<String> submitReview(@RequestParam("order_id") String orderId,
                                               @RequestParam("product_id") int product_id,
                                               @RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               @RequestParam("rating") int rating,
                                               @RequestParam("photo") MultipartFile photo,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity response = null;
        try {
            // 리뷰 저장 로직 구현
            Users users = userService.findById(userDetails.getUsername());
            List<Purchases> purchases = purchaseService.findByProductIdAndOrderId(product_id, orderId);
            if(purchases.size() > 0){
                Purchases pur = purchaseService.findByThreeId(users.getId(),product_id,orderId);
                Products p = pur.getProducts();
                reviewService.writeReview(users, p, title, content, rating, photo, pur);
                p.setRatingSum(p.getRatingSum() + rating);
                p.setRatingCnt(p.getRatingCnt()+1);
                productService.updateProduct(p);
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("리뷰가 등록되었습니다!");
            }
            else throw new Exception();
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }
}
