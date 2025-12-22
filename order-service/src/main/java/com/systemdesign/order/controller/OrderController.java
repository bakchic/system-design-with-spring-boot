package com.systemdesign.order.controller;

import com.systemdesign.order.model.Order;
import com.systemdesign.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order create(
            @RequestHeader("Idempotency-Key") String key,
            @RequestParam Long userId,
            @RequestParam double amount) {

        return service.createOrder(userId, amount, key);
    }

    @PostMapping("/{orderId}")
    public String placeOrder(@PathVariable Long orderId) {
        return service.placeOrder(orderId).join();
    }
}