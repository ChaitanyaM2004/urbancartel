package com.urbancartel.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity//marks class as entity
@Table(name="customers")//sets table name as customers
@Data//getters and setters
@NoArgsConstructor//ddefault constructor
@AllArgsConstructor//parameterized constructor
@SuperBuilder//builder pattern
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(length = 1)
    private String gender; // 'M', 'F', 'O'

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

}
