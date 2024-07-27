package com.shoppingmall.service;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.CategoryRepository;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;  // 파일 저장 서비스

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, FileStorageService fileStorageService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.fileStorageService = fileStorageService;
        this.categoryRepository = categoryRepository;
    }


    public void saveProduct(String product_name, String description, int price, MultipartFile file, int category, String seller_id) {
        // 파일 저장
        String photoPath = fileStorageService.storeFile(file);

        Users user = userRepository.findById(seller_id);

        // 상품 생성
        Products product = new Products();
        product.setProduct_name(product_name);
        product.setDescription(description);
        product.setPrice(price);
        product.setPhoto(photoPath);
        product.setUser(user);  // user를 설정하면 자동으로 userId도 설정됨
        product.setSeller_id(user.getId());
        product.setCategory(category);

        // 상품 저장
        productRepository.save(product);
    }

    public List<Products> searchByName(String name){
        return productRepository.findByName(name);
    }

    public List<Products> searchByCategory(String category){
        List<Integer> id = categoryRepository.findIdByTitle(category);
        List<Products> products = new ArrayList<>();
        for(int categoryId : id){
            products.addAll(productRepository.findByCategory(categoryId));
        }
        return products;
    }

    public List<Products> searchBySellerId(String seller_id){
        return productRepository.findBySellerId(seller_id);
    }

    public Products findById(int id) { return productRepository.findById(id); }
}
