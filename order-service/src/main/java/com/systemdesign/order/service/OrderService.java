package com.systemdesign.order.service;

import com.systemdesign.order.client.PaymentClient;
import com.systemdesign.order.model.Order;
import com.systemdesign.order.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PaymentClient paymentClient;

    public OrderService(OrderRepository repository,
                        KafkaTemplate<String, String> kafkaTemplate, PaymentClient paymentClient) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentClient = paymentClient;
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
                    String orderMessage = order.getId()+":"+order.getAmount()+":"+order.getStatus();
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

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    @Retry(name = "paymentService")
    @TimeLimiter(name = "paymentService")
    public CompletableFuture<String> placeOrder(Long orderId) {

        System.out.println("Order is being placed");

        return CompletableFuture.supplyAsync(() ->
                paymentClient.makePayment(orderId)
        );
    }

    /**
     * FALLBACK METHOD
     */
    public CompletableFuture<String> paymentFallback(Long orderId, Exception ex) {

        return CompletableFuture.completedFuture(
                "PAYMENT SERVICE UNAVAILABLE. ORDER SAVED AS PENDING."
        );
    }
}