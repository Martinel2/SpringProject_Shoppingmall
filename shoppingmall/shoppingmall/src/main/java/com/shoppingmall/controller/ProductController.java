package com.shoppingmall.controller;

import com.shoppingmall.domain.Products;
import com.shoppingmall.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products/add")
    public ResponseEntity<String> addProduct(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "price") int price,
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "category") int category,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        ResponseEntity response = null;
        try {
            String userId = userDetails.getUsername();
            productService.saveProduct(name, description, price, file, category, userId);
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("상품이 등록되었습니다!");
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @GetMapping("/products/{id}")
    public String getProductDetail(@PathVariable("id") int id,
                                   @RequestParam(value = "query", required = false) String keyword,
                                   @RequestParam(value = "category", required = false) String category,
                                   @RequestParam(value = "sellerId", required = false) String sellerId,
                                   Model model) {
        Products product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("query", keyword); // 검색 쿼리를 모델에 추가
        model.addAttribute("category", category); // 검색 쿼리를 모델에 추가
        model.addAttribute("sellerId", sellerId); // 검색 쿼리를 모델에 추가

        return "product/productDetail"; // 상세 페이지의 템플릿 이름
    }

}

