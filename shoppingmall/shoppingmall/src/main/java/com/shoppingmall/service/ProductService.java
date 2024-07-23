package com.shoppingmall.service;

import com.shoppingmall.domain.Category;
import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.repository.UserRepository;
import com.shoppingmall.session.SessionConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;  // 파일 저장 서비스

    public Products saveProduct(String product_name, String description, int price, MultipartFile file, int categoryId, String seller_id) {
        // 파일 저장
        String photoPath = fileStorageService.storeFile(file);

        Users user = userRepository.findById(seller_id);

        // 상품 생성
        Products product = new Products();
        product.setSeller_id(SessionConst.sessionId);
        product.setProduct_name(product_name);
        product.setDescription(description);
        product.setPrice(price);
        product.setPhoto(photoPath);
        product.setUser(user);  // user를 설정하면 자동으로 userId도 설정됨

        // 카테고리 설정
        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);

        // 상품 저장
        return productRepository.save(product);
    }
}
