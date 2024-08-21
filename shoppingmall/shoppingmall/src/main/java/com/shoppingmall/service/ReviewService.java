package com.shoppingmall.service;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Review;
import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.ReviewRepository;
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

    public Review writeReview(Users user, Products products, String title, String content, int rating, MultipartFile photo){
        String photoPath = "";

        if(photo != null)
            photoPath= fileStorageService.storeFile(photo);

        // 리뷰 저장 로직 구현
        Review review = new Review();
        review.setUsers(user);
        review.setProducts(products);
        review.setContent(content);
        review.setTitle(title);
        review.setRating(rating);
        if(photoPath.length() > 0) review.setPhoto(photoPath);
        return reviewRepository.writeReview(review);
    }

    public List<Review> findReviewByUserId(String user_id){
        return reviewRepository.getReviewByUserId(user_id);
    }
}
