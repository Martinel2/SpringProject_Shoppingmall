package com.shoppingmall.controller;

import com.shoppingmall.domain.Cart;
import com.shoppingmall.domain.Products;
import com.shoppingmall.service.CartService;
import com.shoppingmall.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;

    public ProductController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping(value = "/products/add")
    public String createForm() {
        return "product/productAdd";
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

    @GetMapping("/cart/{user_id}")
    public String getCart(@PathVariable("user_id") String user_id,
                            Model model){
        List<Cart> carts = new ArrayList<>();
        carts = cartService.getCart(user_id);
        int total = 0;
        for(int i = 0; i<carts.size(); i++){
            Products p = carts.get(i).getProducts();
            total += (carts.get(i).getQuantity()*p.getPrice());
        }
        model.addAttribute("carts", carts);
        model.addAttribute("total", total);
        return "cart";
    }
}

