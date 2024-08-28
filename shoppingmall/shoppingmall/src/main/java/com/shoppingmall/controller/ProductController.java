package com.shoppingmall.controller;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Wishlist;
import com.shoppingmall.dto.ProductDto;
import com.shoppingmall.service.CartService;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.WishlistService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    private final CartService cartService;

    private final WishlistService wishlistService;

    public ProductController(ProductService productService, CartService cartService, WishlistService wishlistService) {
        this.productService = productService;
        this.cartService = cartService;
        this.wishlistService = wishlistService;
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
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model) {
        Products products = productService.findById(id);
        int price = products.getPrice();
        int discountPrice = (int) (price * (1-products.getDiscount()/100));
        boolean isWishlist = false;
        int wishlistCnt = wishlistService.getAllWishlistByProductId(id).size();
        if(userDetails !=null) {
            Wishlist wishlist = wishlistService.findByTwoId(userDetails.getUsername(), id);
            if (wishlist != null) isWishlist = true;
        }
        ProductDto productDtos = new ProductDto(products,(int)products.getDiscount(),discountPrice,isWishlist,wishlistCnt);
        model.addAttribute("productDto", productDtos);
        model.addAttribute("query", keyword); // 검색 쿼리를 모델에 추가
        model.addAttribute("category", category); // 검색 쿼리를 모델에 추가
        model.addAttribute("sellerId", sellerId); // 검색 쿼리를 모델에 추가
        return "product/productDetail"; // 상세 페이지의 템플릿 이름
    }

    @PostMapping("/products/modPrice")
    public ResponseEntity<String> modPrice(@RequestParam(name = "product_id") int product_id, @RequestParam(name = "modPrice") int modPrice){
        ResponseEntity response = null;
        try {
            Products products = productService.findById(product_id);
            products.setPrice(modPrice);
            if(productService.updateProduct(products)) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("가격이 변경되었습니다.");
            } else{
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("업데이트 실패");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @PostMapping("/products/delete")
    public ResponseEntity<String> deletePrice(@RequestParam(name = "product_id") int product_id){
        ResponseEntity response = null;
        try {
            cartService.deleteAllProductById(product_id);
            if(productService.deleteProduct(product_id)) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("상품이 성공적으로 판매 종료 되었습니다.");
            } else{
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("삭제 실패");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/product")
    public String adminPage(Model model){
        List<Products> productsList = new ArrayList<>();
        productsList = productService.getAllProduct();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Products product:productsList) {
            int price = product.getPrice();
            int discountPrice = (int) (price * (1-product.getDiscount()/100));
            productDtos.add(new ProductDto(product,discountPrice));
        }
        model.addAttribute("productDto", productDtos);
        return "/user/admin";
    }

    @PostMapping("/products/discount")
    public ResponseEntity<String> discountProduct(@RequestParam(name = "product_id") int product_id,
                                                  @RequestParam(name = "discount") int discount){
        ResponseEntity response;
        try {
            if(productService.discount(product_id, discount)) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("할인율 설정 완료");
            } else{
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("업데이트 실패");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

}

