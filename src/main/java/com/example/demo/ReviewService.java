package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Add Review
    public Review addReview(Long userId,
                            Long productId,
                            Review review) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        review.setUser(user);
        review.setProduct(product);
        review.setReviewDate(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    // View Reviews
    public List<Review> getReviews(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    // Delete Review
    public String deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewRepository.delete(review);

        return "Review deleted successfully";
    }
}