package com.systemdesign.controller;

import com.systemdesign.model.OrderStats;
import com.systemdesign.model.PaymentStats;
import com.systemdesign.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/orders")
    public List<OrderStats> getOrderStats() {
        return analyticsService.getAllOrders();
    }

    @GetMapping("/payments")
    public List<PaymentStats> getPaymentStats() {
        return analyticsService.getAllPayments();
    }
}