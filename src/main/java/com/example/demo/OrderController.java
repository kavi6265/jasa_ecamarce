package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Cart Checkout Endpoint (Updated to receive addressId)
    @PostMapping("/checkout")
    public Order checkout(@RequestParam Long userId,
                           @RequestParam Long addressId) {
        return orderService.checkout(userId, addressId);
    }

    // Direct Buy Now Endpoint (Updated to receive addressId)
    @PostMapping("/buy-now")
    public Order buyNowCheckout(@RequestParam Long userId,
                                @RequestParam Long addressId,
                                @RequestParam Long productId,
                                @RequestParam Integer quantity) {
        return orderService.buyNowCheckout(userId, addressId, productId, quantity);
    }

    @GetMapping("/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) {
        return orderService.getOrders(userId);
    }

    @GetMapping("/details/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PutMapping("/{orderId}")
    public Order updateStatus(@PathVariable Long orderId,
                              @RequestParam String status) {
        return orderService.updateStatus(orderId, status);
    }
}