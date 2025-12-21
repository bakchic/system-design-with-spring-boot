package com.systemdesign.order.service;

import com.systemdesign.order.model.Order;
import com.systemdesign.order.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderService(OrderRepository repository,
                        KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Order createOrder(Long userId, double amount, String idempotencyKey) {

        return repository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {

                    System.out.println("Saving order into PG");

                    Order order = new Order();
                    order.setUserId(userId);
                    order.setAmount(amount);
                    order.setStatus("CREATED");
                    order.setIdempotencyKey(idempotencyKey);

                    Order saved = repository.save(order);

                    System.out.println("Order saved into PG");


                    System.out.println("Creating order event into Kafka");
                    String orderMessage = order.getId()+":"+order.getAmount();
                    System.out.println("orderMessage value:"+orderMessage);
                    kafkaTemplate.send(
                            "order-events",
                            saved.getId().toString(),
                            orderMessage
                    );

                    System.out.println("Order event created into Kafka");

                    return saved;
                });
    }
}