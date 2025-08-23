package com.urbancartel.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

@Entity
@Table(name="addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)//many addresses can belong to one customer and fetch type is lazy
    @JoinColumn(name="customer_id", nullable=false)//foreign key column
    private Customer customer;
    @Column(name="address_line1", nullable=false, length=255)
    private String addressLine1;
    @Column(name="address_line2",length=255)
    private String addressLine2;
    @Column(length=50)
    private String city;
    @Column(length=50)
    private String state;
    @Column(name="postal_code", length=20)
    private String postalCode;
    @Column(length=50)
    private String country;

}
