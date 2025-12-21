package com.systemdesign.service;

import com.systemdesign.model.OrderStats;
import com.systemdesign.model.PaymentStats;
import com.systemdesign.repository.OrderStatsRepository;
import com.systemdesign.repository.PaymentStatsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnalyticsService {

    private final OrderStatsRepository orderRepo;
    private final PaymentStatsRepository paymentRepo;

    public AnalyticsService(OrderStatsRepository orderRepo, PaymentStatsRepository paymentRepo) {
        this.orderRepo = orderRepo;
        this.paymentRepo = paymentRepo;
    }

    public void saveOrderEvent(String orderId, String status) {
        OrderStats stats = new OrderStats();
        stats.setOrderId(orderId);
        stats.setStatus(status);
        stats.setCreatedAt(LocalDateTime.now());
        orderRepo.save(stats);
    }

    public void savePaymentEvent(String orderId, String status, Double amount) {
        PaymentStats stats = new PaymentStats();
        stats.setOrderId(orderId);
        stats.setStatus(status);
        stats.setAmount(amount);
        stats.setCreatedAt(LocalDateTime.now());
        paymentRepo.save(stats);
    }

    public List<OrderStats> getAllOrders() {
        return orderRepo.findAll();
    }

    public List<PaymentStats> getAllPayments() {
        return paymentRepo.findAll();
    }
}
