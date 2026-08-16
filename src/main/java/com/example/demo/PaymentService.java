package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Payment makePayment(Long orderId, String paymentMethod) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        order.setStatus("PAID");
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }
}