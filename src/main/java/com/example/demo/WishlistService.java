package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Add product to wishlist
    public Wishlist addToWishlist(Long userId, Long productId) {

        if (wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new RuntimeException("Product already in wishlist");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);

        return wishlistRepository.save(wishlist);
    }

    // View wishlist
    public List<Wishlist> getWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    // Remove from wishlist
    public String removeFromWishlist(Long wishlistId) {

        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        wishlistRepository.delete(wishlist);

        return "Removed from wishlist";
    }
}