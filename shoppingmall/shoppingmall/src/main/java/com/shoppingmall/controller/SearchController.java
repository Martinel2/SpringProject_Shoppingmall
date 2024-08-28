package com.shoppingmall.controller;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Wishlist;
import com.shoppingmall.dto.ProductDto;
import com.shoppingmall.service.ProductService;
import com.shoppingmall.service.UserService;
import com.shoppingmall.service.WishlistService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private final ProductService productService;
    private final WishlistService wishlistService;
    private final UserService userService;
    public SearchController(ProductService productService, WishlistService wishlistService, UserService userService) {
        this.productService = productService;
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public String search( @RequestParam(value = "query", required = false) String keyword,
                          @RequestParam(value = "category", required = false) String category,
                          @RequestParam(value = "sellerId", required = false) String sellerId,
                          @AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        // 여기서 query는 요청 파라미터의 이름입니다.
        // 예를 들어, /search?query=검색어 형식으로 요청이 들어온다면,
        // "검색어" 부분이 query 매개변수로 전달됩니다.

        List<Products> products = new ArrayList<>();

        if (category != null && !category.isEmpty()) {
            products = productService.searchByCategory(category);
        } else if (sellerId != null) {
            products = productService.searchBySellerId(sellerId);
        }else products = productService.searchByName(keyword);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Products product:products) {
            int price = product.getPrice();
            int discountPrice = (int) (price * (1-product.getDiscount()/100));
            boolean isWishlist = false;
            int wishlistCnt = wishlistService.getAllWishlistByProductId(product.getId()).size();
            if(userDetails !=null) {
                Wishlist wishlist = wishlistService.findByTwoId(userDetails.getUsername(), product.getId());
                if (wishlist != null) isWishlist = true;
            }
            productDtos.add(new ProductDto(product,(int)product.getDiscount(),discountPrice,isWishlist,wishlistCnt));
        }
        model.addAttribute("query", keyword);
        model.addAttribute("category", category);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("productDto", productDtos);
        // 검색결과를 표시할 템플릿 이름을 반환합니다.
        return "searchResult"; // search-result.html과 같은 템플릿 파일을 찾게 됩니다.
    }
}

