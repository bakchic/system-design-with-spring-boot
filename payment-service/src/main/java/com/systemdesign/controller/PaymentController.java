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

    @PostMapping("/process/{orderId}")
    public String process(@PathVariable Long orderId) throws InterruptedException {

        // simulate slowness or failure
        if (orderId % 2 == 0) {
            Thread.sleep(3000); // timeout
        }

        if (orderId % 3 == 0) {
            throw new RuntimeException("Payment failed");
        }

        return "PAYMENT SUCCESS FOR ORDER " + orderId;
    }
}