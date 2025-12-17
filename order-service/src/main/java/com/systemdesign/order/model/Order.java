package com.systemdesign.order.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "orders",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_idempotency", columnNames = "idempotencyKey")
        }
)
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private double amount;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private String idempotencyKey;
}