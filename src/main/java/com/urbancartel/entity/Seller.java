package com.urbancartel.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name="user_id")
public class Seller extends User {
    @Column(nullable = false, length = 100)
    private String shopName;

    @Column(nullable=false,unique=true,length=250)
    private String sellerName;

    @Column(nullable = false, length = 15)
    private String gstNumber;

    @Column(nullable = false, length = 50)
    private String bankAccount;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(length = 50)
    private String country;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "is_active")
    private Boolean isActive = true;


    @OneToMany(mappedBy="seller",cascade=CascadeType.ALL , orphanRemoval=true)
    @ToString.Exclude
    private List<Product> products;



}
