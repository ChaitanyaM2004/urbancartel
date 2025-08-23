package com.urbancartel.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
