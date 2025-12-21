package com.systemdesign.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(
        indexes = {
                @Index(name = "idx_name", columnList = "name"),
                @Index(name = "idx_category", columnList = "category")
        }
)
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String category;

    private double price;

    // denormalized field (for faster reads)
    private String displayText;
}
