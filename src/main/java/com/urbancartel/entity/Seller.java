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

    @Column(nullable=false,length=50)
    private Roles role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "blocked_reason")
    private String blockedReason;

    @Column(name = "blocked_At")
    private LocalDateTime blockedAt;

    @Column(name="unblocked_At")
    private LocalDateTime unblockedAt;


    @OneToMany(mappedBy="seller",cascade=CascadeType.ALL , orphanRemoval=true)
    @ToString.Exclude//since we have used @Data annotation and it generats the toString method and there can b emany products whichj is a sensitive information so i want to exclude the information
    private List<Product> products;
}
