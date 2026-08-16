package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddressRepository addressRepository; // Added AddressRepository

    // 1. STANDARD CART CHECKOUT WITH ADDRESS
    @Transactional
    public Order checkout(Long userId, Long addressId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(product.getName() + " is out of stock");
            }
        }

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address); // Attached Address to Order
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);
        }

        order.setTotalAmount(total);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }

    // 2. DIRECT BUY NOW CHECKOUT WITH ADDRESS
    @Transactional
    public Order buyNowCheckout(Long userId, Long addressId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException(product.getName() + " is out of stock");
        }

        // Reduce product stock
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address); // Attached Address to Order
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        // Create Order Item for direct purchase
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(product.getPrice());

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(quantity));

        order.setTotalAmount(total);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.save(orderItem);

        return savedOrder;
    }

    public List<Order> getOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public Order updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }
}