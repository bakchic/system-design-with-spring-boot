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
        payment.setCreatedAt(LocalDateTime.now());

        // Simulate failure
        if(Double.compare(amount, 1000.0) > 0) {
            payment.setStatus("FAILED");
        } else {
            payment.setStatus("SUCCESS");
        }

        Payment saved = paymentRepository.save(payment);

        // Publish Kafka event
        paymentProducer.sendPaymentEvent(orderId + ":" + payment.getStatus());

        return saved;
    }
}