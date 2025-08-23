package com.urbancartel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @Column(name="order_id",nullable=false)
    private Order order;

    @Column(name="payment_date",nullable=false)
    private java.time.LocalDateTime paymentDate = java.time.LocalDateTime.now();

    @Column(name="amount",nullable=false)
    private java.math.BigDecimal amount;

    @Column(name="payment_method",nullable=false,length=50)
    private String paymentMethod; // e.g., 'CREDIT_CARD', 'PAYPAL',
}
