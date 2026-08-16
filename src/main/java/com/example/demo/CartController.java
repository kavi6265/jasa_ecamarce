package com.example.demo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add item to cart
    @PostMapping("/add")
    public Cart addToCart(@RequestParam("userId") Long userId,
                          @RequestParam("productId") Long productId,
                          @RequestParam("quantity") Integer quantity) {

        return cartService.addProductToCart(userId, productId, quantity);
    }

    // Get all cart items for user
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable("userId") Long userId) {

        System.out.println("Inside getCart(), userId = " + userId);

        return cartService.getCart(userId);
    }

    // Test endpoint
    @GetMapping("/test")
    public String test() {
        return "Cart Controller Working";
    }

    // Update quantity of item in cart
    @PutMapping("/item/{cartItemId}")
    public CartItem updateQuantity(@PathVariable("cartItemId") Long cartItemId,
                                   @RequestParam("quantity") Integer quantity) {

        return cartService.updateQuantity(cartItemId, quantity);
    }

    // Remove single item from cart
    @DeleteMapping("/item/{cartItemId}")
    public String removeCartItem(@PathVariable("cartItemId") Long cartItemId) {
        return cartService.removeCartItem(cartItemId);
    }

    // Get total price of user cart
    @GetMapping("/total/{userId}")
    public BigDecimal getCartTotal(@PathVariable("userId") Long userId) {
        return cartService.getCartTotal(userId);
    }
}