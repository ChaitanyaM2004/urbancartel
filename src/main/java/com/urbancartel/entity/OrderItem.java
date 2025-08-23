package com.urbancartel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id",nullable=false)
    private Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id",nullable=false)
    private Product product;

    @Column(name="quantity",nullable = false)
    private int quantity;

    @Column(name="price",nullable = false)
    private java.math.BigDecimal price;


}
