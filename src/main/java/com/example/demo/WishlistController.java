package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public Wishlist addToWishlist(@RequestParam Long userId,
                                  @RequestParam Long productId) {

        return wishlistService.addToWishlist(userId, productId);
    }

    @GetMapping("/{userId}")
    public List<Wishlist> getWishlist(@PathVariable Long userId) {

        return wishlistService.getWishlist(userId);
    }

    @DeleteMapping("/{wishlistId}")
    public String removeFromWishlist(@PathVariable Long wishlistId) {

        return wishlistService.removeFromWishlist(wishlistId);
    }
}