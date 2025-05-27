package com.shoppingmall.Product.Controller;

import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.Wishlist.Domain.Wishlist;
import com.shoppingmall.Product.Dto.ProductDto;
import com.shoppingmall.Product.Service.CategoryService;
import com.shoppingmall.Product.Service.ProductService;
import com.shoppingmall.Wishlist.Service.WishlistService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    private final ProductService productService;
    private final WishlistService wishlistService;

    private final CategoryService categoryService;

    public SearchController(ProductService productService, WishlistService wishlistService, CategoryService categoryService) {
        this.productService = productService;
        this.wishlistService = wishlistService;
        this.categoryService = categoryService;
    }

    @GetMapping("/search")
    public String search( @RequestParam(value = "query", required = false) String keyword,
                          @RequestParam(value = "category", required = false) String category,
                          @RequestParam(value = "sellerId", required = false) String sellerId,
                          @RequestParam(value = "sort" , required = false, defaultValue = "0") int sort,
                          @RequestParam(value = "view", required = false,defaultValue = "0") int view,
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

        Comparator<Products> byReviewCount = (o1, o2) -> Long.compare(o2.getRatingCnt(), o1.getRatingCnt());
        Comparator<Products> byAverageRating = (o1, o2) -> Double.compare(o2.getAverageRating(), o1.getAverageRating());
        Comparator<Products> byPriceLowToHigh = Comparator.comparingDouble(o -> o.getPrice() * (1 - o.getDiscount() / 100));
        Comparator<Products> byPriceHighToLow = (o1, o2) -> Double.compare(o2.getPrice() * (1 - o2.getDiscount() / 100), o1.getPrice() * (1 - o1.getDiscount() / 100));
        Comparator<Products> byDiscount = (o1, o2) -> Double.compare(o2.getDiscount(), o1.getDiscount());

        if (sort == 1) {
            products.sort(byReviewCount);
        } else if (sort == 2) {
            products.sort(byAverageRating);
        } else if (sort == 3) {
            products.sort(byPriceLowToHigh);
        } else if (sort == 4) {
            products.sort(byPriceHighToLow);
        } else {  // 디폴트: 할인율 높은 순
            products.sort(byDiscount);
        }


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

        Map<String, List<String>> categoryTree = categoryService.getCategoryTree();
        model.addAttribute("categoryTree", categoryTree);
        model.addAttribute("query", keyword);
        model.addAttribute("category", category);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("sort", sort);
        model.addAttribute("view", view);
        model.addAttribute("productDto", productDtos);
        // 검색결과를 표시할 템플릿 이름을 반환합니다.
        return "searchResult"; // search-result.html과 같은 템플릿 파일을 찾게 됩니다.
    }
}

