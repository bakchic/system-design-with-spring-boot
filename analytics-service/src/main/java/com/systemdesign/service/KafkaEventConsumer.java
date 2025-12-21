package com.systemdesign.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventConsumer {

    private final AnalyticsService analyticsService;

    public KafkaEventConsumer(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @KafkaListener(topics = "order-events", groupId = "analytics-group")
    public void consumeOrderEvent(ConsumerRecord<String, String> record) {
        String message = record.value();
        // message format: "orderId:status"
        String[] parts = message.split(":");
        String orderId = parts[0];
        String status = parts[2];
        analyticsService.saveOrderEvent(orderId, status);
    }

    @KafkaListener(topics = "payment-events", groupId = "analytics-group")
    public void consumePaymentEvent(ConsumerRecord<String, String> record) {
        String message = record.value();
        // message format: "orderId:status:amount"
        String[] parts = message.split(":");
        String orderId = parts[0];
        String status = parts[1];
        Double amount = Double.parseDouble(parts[2]);
        analyticsService.savePaymentEvent(orderId, status, amount);
    }
}
