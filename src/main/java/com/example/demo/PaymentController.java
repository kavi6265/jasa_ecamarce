package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public Payment pay(@RequestParam Long orderId,
                       @RequestParam String paymentMethod) {

        return paymentService.makePayment(orderId, paymentMethod);
    }
}