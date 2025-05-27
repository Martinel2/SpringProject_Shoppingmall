package com.shoppingmall.Review.Service;

import com.shoppingmall.Product.Domain.Products;
import com.shoppingmall.Product.Service.FileStorageService;
import com.shoppingmall.Purchase.Domain.Purchases;
import com.shoppingmall.Review.Domain.Review;
import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Review.Repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FileStorageService fileStorageService;  // 파일 저장 서비스
    public ReviewService(ReviewRepository reviewRepository, FileStorageService fileStorageService) {
        this.reviewRepository = reviewRepository;
        this.fileStorageService = fileStorageService;
    }

    public Review writeReview(Users user, Products products, String title, String content, int rating, MultipartFile photo, Purchases purchases){
        String photoPath = "";

        // 리뷰 저장 로직 구현
        Review review = new Review();
        review.setUsers(user);
        review.setProducts(products);
        review.setContent(content);
        review.setTitle(title);
        review.setRating(rating);
        review.setPurchases(purchases);
        if(photo.getName().contains(".")) {
            photoPath = fileStorageService.storeFile(photo);
            review.setPhoto(photoPath);
        }

        return reviewRepository.writeReview(review);
    }

    public List<Review> findReviewByUserId(String user_id){
        return reviewRepository.getReviewByUserId(user_id);
    }
}
