package com.urbancartel.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity//marks class as entity
@Table(name="customers")//sets table name as customers
@Data//getters and setters and more
@NoArgsConstructor//ddefault constructor
@AllArgsConstructor//parameterized constructor
@SuperBuilder//for inheritance and builder pattern builder is like u are doing naa person.name().age thats builder but in inheritance we cannt do it like it we have to use suoer builder to access the property of parent class
@PrimaryKeyJoinColumn(name = "user_id")//parent is user so we r joining with user table using user_id as foreign key
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

    @Column(nullable=false,length=50)
    private Roles role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "is_active")
    private Boolean isActive = true;

}
