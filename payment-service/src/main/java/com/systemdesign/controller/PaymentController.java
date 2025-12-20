package com.systemdesign.controller;

import com.systemdesign.model.Payment;
import com.systemdesign.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment makePayment(@RequestParam String orderId,
                               @RequestParam Double amount) {
        return paymentService.processPayment(orderId, amount);
    }
}