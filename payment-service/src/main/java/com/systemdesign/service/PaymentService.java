package com.systemdesign.service;

import com.systemdesign.kafka.PaymentProducer;
import com.systemdesign.model.Payment;
import com.systemdesign.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    public PaymentService(PaymentRepository paymentRepository, PaymentProducer paymentProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentProducer = paymentProducer;
    }

    public Payment processPayment(String orderId, Double amount) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus("SUCCESS"); // simple simulation
        payment.setCreatedAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        // Send Kafka event
        paymentProducer.sendPaymentEvent("Payment completed for order: " + orderId);
        System.out.println("Data published into payment-events topic");

        return saved;
    }
}