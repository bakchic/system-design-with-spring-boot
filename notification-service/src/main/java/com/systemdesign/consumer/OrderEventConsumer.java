package com.systemdesign.consumer;

import com.systemdesign.websocket.NotificationWebSocketHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final NotificationWebSocketHandler socketHandler;

    public OrderEventConsumer(NotificationWebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consume(String message) {
        System.out.println("Received event: " + message);
        socketHandler.broadcast("Order Event: " + message);
        System.out.println("Order Event received");
    }
}