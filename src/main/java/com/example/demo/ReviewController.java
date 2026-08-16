package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review addReview(@RequestParam Long userId,
                            @RequestParam Long productId,
                            @RequestBody Review review) {

        return reviewService.addReview(userId, productId, review);
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviews(@PathVariable Long productId) {

        return reviewService.getReviews(productId);
    }

    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId) {

        return reviewService.deleteReview(reviewId);
    }
}