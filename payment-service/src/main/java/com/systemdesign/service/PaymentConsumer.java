package com.systemdesign.service;

import com.systemdesign.model.Payment;
import com.systemdesign.repository.PaymentRepository;
import com.systemdesign.service.PaymentService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    private final PaymentService paymentService;

    public PaymentConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order-events", groupId = "payment-group")
    public void consumeOrderEvent(ConsumerRecord<String, String> record) {
        String message = record.value();
        System.out.println("Received Order Event: " + message);

        // Assume message contains orderId and amount in simple string format
        String orderId = message.split(":")[0];
        Double amount = Double.parseDouble(message.split(":")[1]);

        Payment payment = paymentService.processPayment(orderId, amount);

        // Publish Payment result (already existing logic)
        System.out.println("Payment status: " + payment.getStatus());
    }
}