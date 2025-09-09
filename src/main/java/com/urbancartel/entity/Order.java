package com.urbancartel.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name="order_date",nullable=false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name="status",nullable=false,length=50)
    private String status; // e.g., 'PENDING', 'SHIPPED', '

    @Column(name="total_amount",nullable=false)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> items;

}
